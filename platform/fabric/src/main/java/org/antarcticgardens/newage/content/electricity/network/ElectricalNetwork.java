package org.antarcticgardens.newage.content.electricity.network;

import earth.terrarium.botarium.common.energy.base.PlatformEnergyManager;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import earth.terrarium.botarium.fabric.energy.FabricEnergyManager;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlock;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;

import java.util.*;

public class ElectricalNetwork {
    private final List<ElectricalConnectorBlockEntity> nodes = new ArrayList<>();
    private final Map<ElectricalConnectorBlockEntity, EnergyStorageWrapper> consumers = new HashMap<>();
    private final Map<ElectricalConnectorBlockEntity, EnergyStorageWrapper> pulledSources = new HashMap<>();

    private final ElectricalNetworkPathManager pathManager = new ElectricalNetworkPathManager();

    // Cache for sorted consumers to avoid repeated sorting
    private List<Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper>> sortedConsumersCache = null;
    private boolean consumersCacheValid = false;

    public ElectricalNetwork(ElectricalConnectorBlockEntity base) {
        nodes.add(base);
    }

    public void addNode(ElectricalConnectorBlockEntity node) {
        addNode(node, new ArrayList<>());
        updateConsumersAndSources();
    }

    private void addNode(ElectricalConnectorBlockEntity node, List<ElectricalConnectorBlockEntity> processedNodes) {
        if (!nodes.contains(node))
            nodes.add(node);

        processedNodes.add(node);

        if (node.getNetwork() != this) {
            if (node.getNetwork() != null)
                node.getNetwork().destroy();

            node.setNetwork(this);

            for (ElectricalConnectorBlockEntity connector : node.getConnectors().keySet()) {
                if (!processedNodes.contains(connector))
                    addNode(connector, processedNodes);
            }
        }

        for (ElectricalConnectorBlockEntity connectedNode : node.getConnectors().keySet())
            pathManager.addConnection(node, connectedNode);
    }

    public void updateConsumersAndSources() {
        consumers.clear();
        pulledSources.clear();

        for (ElectricalConnectorBlockEntity node : nodes) {
            if (node.getLevel() != null) {
                Direction dir = node.getBlockState().getValue(BlockStateProperties.FACING);
                BlockEntity entity = node.getLevel().getBlockEntity(node.getSupportingBlockPos());

                if (entity != null && !(entity instanceof ElectricalConnectorBlockEntity) && EnergyHooks.isEnergyContainer(entity, dir)) {
                    PlatformEnergyManager storage = EnergyHooks.getBlockEnergyManager(entity, dir);
                    if (storage instanceof FabricEnergyManager fem) {
                        if (storage.supportsInsertion())
                            consumers.put(node, new EnergyStorageWrapper(entity, fem.energy()));

                        if (storage.supportsExtraction() && node.getBlockState().getValue(ElectricalConnectorBlock.MODE).pull)
                            pulledSources.put(node, new EnergyStorageWrapper(entity, fem.energy()));
                    }
                }
            }
        }

        // Invalidate sorted consumers cache
        consumersCacheValid = false;

        if (!getWorld().isClientSide())
            ElectricalNetworkTicker.addNetwork(this);
    }

    private List<Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper>> getSortedConsumers() {
        if (!consumersCacheValid || sortedConsumersCache == null) {
            sortedConsumersCache = new ArrayList<>(consumers.entrySet());
            sortedConsumersCache.sort((e1, e2) -> {
                double fill1 = e1.getValue().storage().getCapacity() > 0 ?
                        e1.getValue().storage().getAmount() / (double) e1.getValue().storage().getCapacity() : 0;
                double fill2 = e2.getValue().storage().getCapacity() > 0 ?
                        e2.getValue().storage().getAmount() / (double) e2.getValue().storage().getCapacity() : 0;
                return Double.compare(fill1, fill2);
            });
            consumersCacheValid = true;
        }
        return sortedConsumersCache;
    }

    public long insert(ElectricalConnectorBlockEntity from, long amount, boolean simulate) {
        if (consumers.isEmpty())
            return 0;

        NetworkPathConductivityContext context = simulate ?
                new NetworkPathConductivityContext(pathManager.getConductivityContext()) :
                pathManager.getConductivityContext();

        // Flow-based distribution: small chunks with round-robin
        // This ensures fair distribution and adapts to changing network state
        long inserted = 0;
        long chunkSize = calculateAdaptiveChunkSize(amount);
        int iteration = 0;
        final int MAX_ITERATIONS = 100; // Reduced since we use adaptive chunking

        List<Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper>> sortedConsumers = getSortedConsumers();

        while (inserted < amount && iteration < MAX_ITERATIONS) {
            boolean anyProgress = false;
            int consumersWithProgress = 0;

            for (Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper> consumer : sortedConsumers) {
                if (inserted >= amount)
                    break;

                // Try to insert a small chunk
                long actualChunk = Math.min(chunkSize, amount - inserted);
                long actualInsert = insertInto(from, consumer, context, actualChunk, simulate);

                if (actualInsert > 0) {
                    inserted += actualInsert;
                    anyProgress = true;
                    consumersWithProgress++;
                }
            }

            iteration++;
            if (!anyProgress)
                break; // No consumer could accept energy

            // If only one consumer is receiving energy, increase chunk size
            // If many consumers are receiving, decrease for better fairness
            if (consumersWithProgress == 1) {
                chunkSize = Math.min(chunkSize * 2, amount - inserted);
            } else if (consumersWithProgress > 3) {
                chunkSize = Math.max(chunkSize / 2, 10); // Minimum 10
            }
        }

        return inserted;
    }

    private long calculateAdaptiveChunkSize(long totalAmount) {
        // Use smaller chunks for small amounts, larger for big amounts
        if (totalAmount < 100) return 10;
        if (totalAmount < 1000) return 50;
        if (totalAmount < 10000) return 100;
        return 200;
    }

    private long insertInto(ElectricalConnectorBlockEntity from,
                            Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper> to,
                            NetworkPathConductivityContext context,
                            long amount,
                            boolean simulate) {
        if (from == null || to == null || to.getValue() == null || context == null) {
            return 0;
        }

        long inserted = 0;

        // Try to use any available path to transfer energy
        NetworkPath path = pathManager.findConductiblePath(from, to.getKey());
        if (path == null) {
            return 0;
        }

        long pathConductivity = context.calculatePathConductivity(path);
        if (pathConductivity <= 0) {
            return 0;
        }

        // Try to insert energy, limited by path conductivity and consumer capacity
        long actualInsert = to.getValue().insert(Math.min(pathConductivity, amount), simulate);

        if (actualInsert > 0) {
            if (!simulate) {
                BlockEntity be = to.getValue().entity();
                if (be != null) {
                    be.setChanged();
                    if (be.getLevel() instanceof ServerLevel serverLevel)
                        serverLevel.getChunkSource().blockChanged(be.getBlockPos());
                }
            }

            // Decrease path conductivity for all paths this transmission used
            context.decreasePathConductivity(path, actualInsert);
            inserted += actualInsert;
        }

        return inserted;
    }

    public void destroy() {
        ElectricalNetworkTicker.removeNetwork(this);
        for (ElectricalConnectorBlockEntity node : nodes)
            node.setNetwork(new ElectricalNetwork(node));
    }

    protected void tick() {
        for (Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper> e : pulledSources.entrySet()) {
            // Only extract if there are consumers to receive energy
            if (consumers.isEmpty()) {
                continue;
            }

            long maxExtracted = e.getValue().extract(Long.MAX_VALUE, true);
            if (maxExtracted <= 0) {
                continue; // No energy available
            }

            long inserted = insert(e.getKey(), maxExtracted, false);
            if (inserted > 0) {
                e.getValue().extract(inserted, false);
            }
        }

        pathManager.tick();
    }

    public Level getWorld() {
        if (nodes.isEmpty()) {
            return null;
        }
        return nodes.get(0).getLevel();
    }

    public List<ElectricalConnectorBlockEntity> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
    
    public ElectricalNetworkPathManager getPathManager() {
        return pathManager;
    }
}
