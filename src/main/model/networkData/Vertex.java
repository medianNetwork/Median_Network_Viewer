package main.model.networkData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Chriz on 14.09.2016.
 */
public class Vertex {
    private int id;
    private Set<Integer> adjacenticEdgeIds;
    private Set<Integer> splitIdSet;
    private String label;
    private StringProperty labelProperty;
    private DoubleProperty posXProperty;
    private DoubleProperty posYProperty;

    private Vertex(int id) {
        labelProperty = new SimpleStringProperty("");
        this.id = id;
        posXProperty = new SimpleDoubleProperty();
        posYProperty = new SimpleDoubleProperty();
        this.adjacenticEdgeIds = new HashSet<>();
        this.splitIdSet = new HashSet<>();
    }

    public Vertex(int id, double posX, double posY) {
        this(id);
        this.posXProperty.setValue(posX);
        this.posYProperty.setValue(posY);
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return labelProperty.get();
    }

    public void addAdjacenticEdge(int edgeId) {
        this.adjacenticEdgeIds.add(edgeId);
    }

    public void assignSplitIdRecursively(int splitId, ConcurrentHashMap<Integer, Edge> idToEdge, ConcurrentHashMap<Integer, Vertex> idToVertex) {
        int graphSplitId = (splitId < 0) ? -splitId : splitId;
        if (this.splitIdSet.contains(splitId)) {
            return;
        }
        this.splitIdSet.add(splitId);
        adjacenticEdgeIds.forEach(edgeId -> {
            if (idToEdge.get(edgeId).getSplitId() != graphSplitId) {
                idToVertex.get(idToEdge.get(edgeId).getCorrespondingNodeIdFrom(this.id)).assignSplitIdRecursively(splitId, idToEdge, idToVertex);
            }
        });
    }

    public double getPosX() {
        return posXProperty.get();
    }

    public double getPosY() {
        return posYProperty.get();
    }

    public DoubleProperty getPosXProperty() {
        return posXProperty;
    }

    public DoubleProperty getPosYProperty() {
        return posYProperty;
    }

    public Set<Integer> getAdjacenticEdgeIds() {
        return adjacenticEdgeIds;
    }

    public Set<Integer> getSplitIdSet() {
        return splitIdSet;
    }
}
