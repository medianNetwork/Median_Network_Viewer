package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by NW on 22.02.2017.
 */
public class Parser {

    /**
     * Parse the file to internal project data
     * @param file
     */
    public static Network parse(File file) {

        Map<Integer, Point2D> vertices = new HashMap<>();
        Map<Integer, String> vLabels = new HashMap<>();
        Map<Integer, Pair<Integer, Integer>> edges = new HashMap<>();

        ObservableList<ObservableList> csvData = FXCollections.observableArrayList();
        try {
            Scanner s = new Scanner(file).useDelimiter("\\n");
            while (s.hasNext()) {
                String current = s.next();




                // Get the vertices
                if(current.startsWith("VERTICES")){
                    while(s.hasNext()){
                        current = s.next();
                        if(current.startsWith(";")){
                            break;
                        }
                        String[] splits = current.split(" ");
                        // TODO Length check
                        Integer index = Integer.parseInt(splits[0]);
                        Double xCoord = Double.parseDouble(splits[1]);
                        Double yCoord = Double.parseDouble(splits[2]);
                        vertices.put(index, new Point2D(xCoord, yCoord));
                    }
                }

                // Get the vLabels
                if(current.startsWith("VLABELS")){
                    while(s.hasNext()){
                        current = s.next();
                        if(current.startsWith(";")){
                            break;
                        }
                        String[] splits = current.split(" ");
                        // TODO Length check
                        Integer index = Integer.parseInt(splits[0]);
                        String name = splits[1];
                        vLabels.put(index, name);
                    }
                }

                // Get the edges
                if(current.startsWith("EDGES")){
                    while(s.hasNext()){
                        current = s.next();
                        if(current.startsWith(";")){
                            break;
                        }
                        String[] splits = current.split("[ ,]");
                        // TODO Length check
                        Integer index = Integer.parseInt(splits[0]);
                        Integer vert1 = Integer.parseInt(splits[1]);
                        Integer vert2 = Integer.parseInt(splits[2]);
                        edges.put(index, new Pair(vert1, vert2));
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }

        return new Network(vertices,vLabels ,edges);
    }
}
