package org.antarcticgardens.newage.content.electricity.network;

import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;

import java.util.*;

public class ElectricalNetworkPathManager {
    private NetworkPathConductivityContext context = new NetworkPathConductivityContext();
    private final Map<NetworkPathKey<ElectricalConnectorBlockEntity>, List<NetworkPath>> pathCache = new HashMap<>();

    protected void addConnection(ElectricalConnectorBlockEntity node, ElectricalConnectorBlockEntity node1) {
        context.addConnection(node, node1);
        invalidateCache();
    }

    private void invalidateCache() {
        pathCache.clear();
    }

    protected NetworkPath findConductiblePath(ElectricalConnectorBlockEntity a, ElectricalConnectorBlockEntity b) {
        NetworkPathKey<ElectricalConnectorBlockEntity> key = new NetworkPathKey<>(a, b);

        // Check cache first
        List<NetworkPath> cachedPaths = pathCache.get(key);
        if (cachedPaths != null) {
            for (NetworkPath path : cachedPaths) {
                if (context.calculatePathConductivity(path) > 0) {
                    return path;
                }
            }
        }

        // If no cached path available, find new paths
        List<NetworkPath> allPaths = findAllConductiblePaths(a, b);
        if (allPaths.isEmpty()) {
            return null;
        }

        // Cache the results
        pathCache.put(key, allPaths);

        return allPaths.isEmpty() ? null : allPaths.get(0);
    }

    /**
     * Get all conductible paths between two connectors
     */
    protected List<NetworkPath> getAllConductiblePaths(ElectricalConnectorBlockEntity a, ElectricalConnectorBlockEntity b) {
        NetworkPathKey<ElectricalConnectorBlockEntity> key = new NetworkPathKey<>(a, b);

        // Check cache first
        List<NetworkPath> cachedPaths = pathCache.get(key);
        if (cachedPaths != null) {
            return cachedPaths;
        }

        // Find and cache all paths
        List<NetworkPath> allPaths = findAllConductiblePaths(a, b);
        if (!allPaths.isEmpty()) {
            pathCache.put(key, allPaths);
        }

        return allPaths;
    }

    private List<NetworkPath> findAllConductiblePaths(ElectricalConnectorBlockEntity a, ElectricalConnectorBlockEntity b) {
        List<NetworkPath> paths = new ArrayList<>();
        Set<ElectricalConnectorBlockEntity> visited = new HashSet<>();
        Queue<QueueElement> queue = new LinkedList<>();
        queue.add(new QueueElement(a, null, 0));
        visited.add(a);

        final int MAX_PATHS = 10; // Limit to prevent excessive memory usage

        while (!queue.isEmpty() && paths.size() < MAX_PATHS) {
            var element = queue.poll();

            if (element.connector.equals(b)) {
                NetworkPath path = unwrapConductiblePath(element);
                if (path != null) {
                    paths.add(path);
                }
                continue; // Continue to find alternative paths
            }

            for (ElectricalConnectorBlockEntity connector : element.connector.getConnectors().keySet()) {
                if (!visited.contains(connector) && element.depth < NewAgeConfig.getCommon().maxPathfindingDepth.get()) {
                    visited.add(connector);
                    queue.add(new QueueElement(connector, element, element.depth + 1));
                }
            }
        }

        return paths;
    }

    private NetworkPath unwrapConductiblePath(QueueElement element) {
        NetworkPath path = new NetworkPath();

        while (element != null) {
            if (path.getLength() != 0 && context.getConnectionConductivity(new NetworkPathKey<>(element.connector, path.getFirstNode())) <= 0)
                return null;

            path.addNodeToBeginning(element.connector);
            element = element.parent;
        }

        if (path.getLength() < 2)
            return null;

        return path;
    }

    protected NetworkPathConductivityContext getConductivityContext() {
        return context;
    }
    
    protected void setConductivityContext(NetworkPathConductivityContext context) {
        this.context = context;
    }

    protected void tick() {
        context.updateConductivity();
        // Clear path cache each tick to recalculate with updated conductivity
        invalidateCache();
    }

    private record QueueElement(ElectricalConnectorBlockEntity connector, QueueElement parent, int depth) { }
}
