package main.model;

import main.model.networkData.Edge;
import main.model.networkData.Split;
import main.model.networkData.Vertex;
import main.model.networkData.Vlabel;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Christopher Juerges on 13/09/16.
 */
public class NexusNetwork {
    private ConcurrentHashMap<Integer, Vertex> idToVertex;
    private ConcurrentHashMap<Integer, Edge> idToEdge;
    private ConcurrentHashMap<Integer, Vlabel> idToLabel;
    private ConcurrentHashMap<Integer, Split> idToSplit;
    private ConcurrentHashMap<Integer, Edge> splitRepresentative;

    public NexusNetwork() {
    }

    public void addData(ConcurrentHashMap<Integer, Vertex> idToVertex,
                        ConcurrentHashMap<Integer, Edge> idToEdge,
                        ConcurrentHashMap<Integer, Vlabel> idToLabel,
                        ConcurrentHashMap<Integer, Split> idToSplit) {
        this.idToVertex = idToVertex;
        this.idToEdge = idToEdge;
        this.idToLabel = idToLabel;
        this.idToSplit = idToSplit;

        addEdgesToVertecies();
        createSplitRepresentative();
        assignSplitIdsToVertices();
    }

    private void addEdgesToVertecies() {
        idToEdge.values().forEach(e -> {
            idToEdge.put(e.getId(), e);
            idToVertex.get(e.getVertexID1()).addAdjacenticEdge(e.getId());
            idToVertex.get(e.getVertexID2()).addAdjacenticEdge(e.getId());
        });
    }

    private void createSplitRepresentative() {
        splitRepresentative = new ConcurrentHashMap<>(getSplitCount());
        for (Edge edge : idToEdge.values()) {
            splitRepresentative.put(edge.getSplitId(), edge);
        }
    }

    public Set<Integer> getSplitAssociatedNodes(int edgeId, int vertexId) {
        Set<Integer> nodeSet = new HashSet<>();
        int splitId = idToEdge.get(edgeId).getSplitId();
        splitId = (idToVertex.get(vertexId).getSplitIdSet().contains(splitId)) ? splitId : -splitId;
        for (Vertex vertex : idToVertex.values()) {
            if (vertex.getSplitIdSet().contains(splitId)) {
                nodeSet.add(vertex.getId());
            }
        }
        return nodeSet;
    }

    private void assignSplitIdsToVertices() {
        for (Edge edge : splitRepresentative.values()) {
            if (edge != null) {
                idToVertex.get(edge.getVertexID1()).assignSplitIdRecursively(edge.getSplitId(), idToEdge, idToVertex);
                idToVertex.get(edge.getVertexID2()).assignSplitIdRecursively(-edge.getSplitId(), idToEdge, idToVertex);
            }
        }
    }

    public int getCorrespondingNodeIdFromSplitId(int vertexId, int splitId) {
        for (Edge edge : idToEdge.values()) {
            if (edge.getSplitId() == splitId && (edge.getVertexID1() == vertexId || edge.getVertexID2() == vertexId)) {
                return edge.getCorrespondingNodeIdFrom(vertexId);
            }
        }
        return -1;
    }

    public ConcurrentHashMap<Integer, Vertex> getIdToVertex() {
        return idToVertex;
    }

    public ConcurrentHashMap<Integer, Edge> getIdToEdge() {
        return idToEdge;
    }

    public int getEdgeCount() {
        return idToEdge.values().size();
    }

    public int getVertexCount() {
        return idToVertex.values().size();
    }

    public int getLabelCount() {
        return idToLabel.values().size();
    }

    public ConcurrentHashMap<Integer, Vlabel> getIdToLabel() {
        return idToLabel;
    }

    public int getSplitCount() {
        return idToSplit.values().size();
    }

    public ConcurrentHashMap<Integer, Split> getIdToSplit() {
        return idToSplit;
    }
}
