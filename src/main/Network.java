package main;

import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by NW on 22.02.2017.
 */
public class Network {

    private Map<Integer, Point2D> vertices;
    private Map<Integer, String> vLabels;
    private Map<Integer, Pair<Integer, Integer>>  edges;

    public Network(Map<Integer, Point2D> vertices, Map<Integer, String> vLabels, Map<Integer, Pair<Integer, Integer>> edges) {
        this.vertices = vertices;
        this.vLabels = vLabels;
        this.edges = edges;
    }

    public Map<Integer, Point2D> getVertices() {
        return vertices;
    }

    public void setVertices(Map<Integer, Point2D> vertices) {
        this.vertices = vertices;
    }

    public Map<Integer, String> getvLabels() {
        return vLabels;
    }

    public void setvLabels(Map<Integer, String> vLabels) {
        this.vLabels = vLabels;
    }

    public Map<Integer, Pair<Integer, Integer>> getEdges() {
        return edges;
    }

    public void setEdges(Map<Integer, Pair<Integer, Integer>> edges) {
        this.edges = edges;
    }
}
