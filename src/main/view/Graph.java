package main.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import main.model.NexusNetwork;
import main.model.networkData.Edge;
import main.model.networkData.Vertex;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by NW on 22.02.2017.
 */
public class Graph {

    private NexusNetwork nexusNetwork;

    private Map<Integer, Node> vertexNodes;
    private Map<Integer, Node> vLabelNodes;
    private Map<Integer, Node> edgeNodes;
    private Map<Integer, Node> splitsNodes;

    private Group group = new Group();


    public Graph(NexusNetwork nexusNetwork) {
        this.nexusNetwork = nexusNetwork;
        this.vertexNodes = new ConcurrentHashMap<>();
        this.vLabelNodes = new ConcurrentHashMap<>();
        this.edgeNodes = new ConcurrentHashMap<>();
        this.splitsNodes = new ConcurrentHashMap<>();
    }

    private void reset(Pane pane){
        this.vertexNodes.clear();
        this.vLabelNodes.clear();
        this.edgeNodes.clear();
        this.splitsNodes.clear();

        pane.getChildren().clear();

        GraphScale scale = calculateScale(pane);

        nexusNetwork.getIdToVertex().forEach((index, vertex)->{
            vertexNodes.put(index, generateVertex(vertex, scale));
        });

        nexusNetwork.getIdToEdge().forEach((index, edge)->{
            edgeNodes.put(index, generateEdge(edge, scale));
        });

        System.out.println(scale.getScaleX());

    }




    private Node generateVertex(Vertex vertex, GraphScale scale){
        Circle circle = new Circle( 5);
        circle.setFill(Color.BLACK);
        circle.setCenterX(vertex.getPosX()*scale.getScaleX() + scale.getWidthMid());
        circle.setCenterY(vertex.getPosY()*scale.getScaleY() + scale.getHeightMid());
        return circle;
    }

    private Node generateEdge(Edge edge, GraphScale scale){
        ConcurrentHashMap<Integer, Vertex> vertices = nexusNetwork.getIdToVertex();
        Line line = new Line();
        line.setFill(Color.BLACK);
        line.setStrokeWidth(1);
        line.setStartX(vertices.get(edge.getVertexID1()).getPosX()*scale.getScaleX() + scale.getWidthMid());
        line.setStartY(vertices.get(edge.getVertexID1()).getPosY()*scale.getScaleY() + scale.getHeightMid());
        line.setEndX(vertices.get(edge.getVertexID2()).getPosX()*scale.getScaleX() + scale.getWidthMid());
        line.setEndY(vertices.get(edge.getVertexID2()).getPosY()*scale.getScaleY() + scale.getHeightMid());
        return line;

    }

    public void drawOnPane(Pane pane){
        reset(pane);
        System.out.println();
        vertexNodes.forEach((index, vertex)->{
            pane.getChildren().add(vertex);
        });
        edgeNodes.forEach((index, edge)->{
            pane.getChildren().add(edge);
        });
    }

    /**
     * Returns a Pair of x and y scales to scale the graph to full pane
     * @param pane
     * @return
     */
    private GraphScale calculateScale(Pane pane){
        // Don't calculate if not at least 2 vertices
        if(nexusNetwork.getIdToVertex().values().size()<= 1){
            return new GraphScale(pane);
        } else {
            double minWidth = Double.MAX_VALUE;
            double maxWidth = Double.MIN_VALUE;
            double minHeight = Double.MAX_VALUE;
            double maxHeight = Double.MIN_VALUE;

            for (Vertex vertex : nexusNetwork.getIdToVertex().values()) {

                double posX = vertex.getPosX();
                double posY = vertex.getPosY();
                if (posX > maxWidth) {
                    maxWidth = posX;
                }
                if (posX < minWidth) {
                    minWidth = posX;
                }
                if (posY > maxHeight) {
                    maxHeight = posY;
                }
                if (posY < minHeight) {
                    minHeight = posY;
                }
            }
            double scaleX = (pane.getWidth() / (maxWidth - minWidth))*0.5;
            double scaleY = (pane.getHeight() / (maxHeight - minHeight))*0.5;

            return new GraphScale(scaleX, scaleY, pane.getHeight(), pane.getWidth());
        }

    }




}
