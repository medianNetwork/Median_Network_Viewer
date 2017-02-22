package main;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NW on 22.02.2017.
 */
public class Graph {

    private Network network;

    private Map<Integer, Node> verticeNodes;
    private Map<Integer, Node> vLabelNodes;
    private Map<Integer, Node> edgeNodes;

    private Group group = new Group();


    public Graph(Network network) {
        this.network = network;
        init();
    }

    private void init(){
        this.verticeNodes = new HashMap<>();
        this.vLabelNodes = new HashMap<>();
        this.edgeNodes = new HashMap<>();

        network.getVertices().forEach((index, point2d) ->{
            verticeNodes.put(index, generateVertice(point2d));
                });

        network.getEdges().forEach((index, pair)->{
            edgeNodes.put(index, generateEdge(pair));
        });



    }




    private Node generateVertice(Point2D point2d){
        Circle circle = new Circle( 2);
        circle.setFill(Color.BLACK);
        circle.setCenterX(point2d.getX()*10);
        circle.setCenterY(point2d.getY()*10);
        return circle;
    }

    private Node generateEdge(Pair<Integer, Integer> pair){
        Map<Integer, Point2D> vertices = network.getVertices();
        Line line = new Line();
        line.setFill(Color.BLACK);
        line.setStrokeWidth(1);
        line.setStartX(vertices.get(pair.getKey()).getX()*10);
        line.setStartY(vertices.get(pair.getKey()).getY()*10);
        line.setEndX(vertices.get(pair.getValue()).getX()*10);
        line.setEndY(vertices.get(pair.getValue()).getY()*10);
        return line;

    }


    public void drawOnPane(Pane pane){
        verticeNodes.forEach((index, node)->{
            pane.getChildren().addAll(node);
        });
        edgeNodes.forEach((index, node)->{
            pane.getChildren().addAll(node);
        });
    }



}
