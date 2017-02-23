package main.model.networkData;

/**
 * Created by Chriz on 14.09.2016.
 */
public class Edge {
    private int id;
    private int vertexID1;
    private int vertexID2;
    private double weight;
    private int splitId;

    public Edge(int id, int v1, int v2) {
        this.id = id;
        this.vertexID1 = v1;
        this.vertexID2 = v2;
    }

    public Edge(int id, int v1, int v2, double weight) {
        this(id, v1, v2);
        this.weight = weight;
    }

    public Edge(int id, int v1, int v2, int splitId) {
        this(id, v1, v2);
        this.splitId = splitId;
    }

    public Edge(int id, int v1, int v2, double weight, int splitId) {
        this(id, v1, v2, weight);
        this.splitId = splitId;
    }

    public int getCorrespondingNodeIdFrom(int currentNodeId) {
        return (currentNodeId == vertexID1) ? vertexID2 : vertexID1;
    }

    public int getId() {
        return id;
    }

    public int getVertexID1() {
        return vertexID1;
    }

    public int getVertexID2() {
        return vertexID2;
    }

    public int getSplitId() {
        return splitId;
    }

    public double getWeight() {
        return weight;
    }
}
