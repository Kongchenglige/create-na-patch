package org.antarcticgardens.newage.content.electricity.network;

import net.minecraft.util.Tuple;
import org.antarcticgardens.newage.content.electricity.connector.ElectricalConnectorBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class NetworkPathConductivityContext {
    private final Map<NetworkPathKey<ElectricalConnectorBlockEntity>, Tuple<Long, Long>> connections;

    public NetworkPathConductivityContext() {
        connections = new HashMap<>();
    }

    public NetworkPathConductivityContext(NetworkPathConductivityContext context) {
        connections = new HashMap<>();

        for (Map.Entry<NetworkPathKey<ElectricalConnectorBlockEntity>, Tuple<Long, Long>> e : context.connections.entrySet())
            connections.put(e.getKey(), new Tuple<>(e.getValue().getA(), e.getValue().getB()));
    }

    public void addConnection(ElectricalConnectorBlockEntity node, ElectricalConnectorBlockEntity node1) {
        if (!node.equals(node1) && !connections.containsKey(new NetworkPathKey<>(node, node1)))
            connections.put(new NetworkPathKey<>(node, node1), new Tuple<>(node.getConnectors().get(node1).getConductivity(), 0L));
    }

    protected long calculatePathConductivity(NetworkPath path) {
        ElectricalConnectorBlockEntity prevNode = null;
        long conductivity = -1; // Use -1 as uninitialized, 0 means no path

        for (ElectricalConnectorBlockEntity node : path.getNodes()) {
            if (prevNode == null) {
                prevNode = node;
                continue;
            }

            NetworkPathKey<ElectricalConnectorBlockEntity> key = new NetworkPathKey<>(prevNode, node);

            if (!connections.containsKey(key))
                return 0;

            long connectionConductivity = connections.get(key).getB();

            // Initialize conductivity with first connection's value
            if (conductivity == -1) {
                conductivity = connectionConductivity;
            } else {
                conductivity = Math.min(connectionConductivity, conductivity);
            }

            prevNode = node;
        }

        return conductivity > 0 ? conductivity : 0;
    }

    protected void decreasePathConductivity(NetworkPath path, long amount) {
        ElectricalConnectorBlockEntity prevNode = null;

        for (ElectricalConnectorBlockEntity node : path.getNodes()) {
            if (prevNode == null) {
                prevNode = node;
                continue;
            }

            NetworkPathKey<ElectricalConnectorBlockEntity> key = new NetworkPathKey<>(prevNode, node);
            long connectionConductivity = connections.get(key).getB();
            long newConductivity = Math.max(0, connectionConductivity - amount); // Prevent negative values
            connections.get(key).setB(newConductivity);
            prevNode = node;
        }
    }

    protected long getConnectionConductivity(NetworkPathKey<ElectricalConnectorBlockEntity> key) {
        return connections.get(key).getB();
    }

    protected void updateConductivity() {
        // Gradually restore conductivity instead of full reset
        // This creates a "cooling" effect for wires
        double recoveryRate = 0.25; // Restore 25% per tick

        for (Map.Entry<NetworkPathKey<ElectricalConnectorBlockEntity>, Tuple<Long, Long>> e : connections.entrySet()) {
            long maxConductivity = e.getValue().getA();
            long currentConductivity = e.getValue().getB();

            if (currentConductivity < maxConductivity) {
                long recoveryAmount = (long) ((maxConductivity - currentConductivity) * recoveryRate);
                long newConductivity = Math.min(maxConductivity, currentConductivity + recoveryAmount);
                connections.get(e.getKey()).setB(newConductivity);
            }
        }
    }
}
