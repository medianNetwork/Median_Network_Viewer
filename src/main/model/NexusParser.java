package main.model;


import main.model.networkData.Edge;
import main.model.networkData.Split;
import main.model.networkData.Vertex;
import main.model.networkData.Vlabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Christopher Juerges on 19/10/16.
 */
public class NexusParser {

    private enum NetworkMode {
        VERTEX, VERTEXswitch, EDGE, EDGEswitch, LABEL, LABELswitch, NONE
    }

    private enum BlockMode {
        NETWORK, NETWORKswitch, SPLIT, SPLITswitch, NONE
    }

    private enum SplitMode {
        MATRIX, MATRIXswitch, NONE
    }

    public NexusParser() {

    }

    public static NexusNetwork parseNetworkData(File file) throws Exception {
        NexusNetwork nexusNetwork = new NexusNetwork();
        NetworkMode networkMode = NetworkMode.NONE;
        BlockMode blockMode = BlockMode.NONE;
        SplitMode splitMode = SplitMode.NONE;
        ConcurrentHashMap<Integer, Vertex> idToVertex = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Edge> idToEdge = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Vlabel> idToLabel = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Split> idToSplit = new ConcurrentHashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        int lineNumber = 1;
        while (line != null) {
            System.out.println("Reading line " + lineNumber);
            lineNumber++;

            // Determine the block we are in
            blockMode = determineBlockMode(line, blockMode);
            if (blockMode.equals(BlockMode.SPLITswitch) || blockMode.equals(BlockMode.NETWORKswitch)) {
                line = br.readLine();
                continue;
            }

            if (blockMode.equals(BlockMode.NETWORK)) {
                // Determine if we read in vertices, labels oder edges
                networkMode = determineNetworkMode(line, networkMode);
                if (networkMode.equals(NetworkMode.LABELswitch) || networkMode.equals(NetworkMode.VERTEXswitch) || networkMode.equals(NetworkMode.EDGEswitch)) {
                    line = br.readLine();
                    continue;
                }

                if (networkMode.equals(NetworkMode.EDGE)) {
                    line = line.replace(",", "");
                    String[] linePieces = line.split(" ");
                    int edgeID = Integer.parseInt(linePieces[0]);
                    int vertex1ID = Integer.parseInt(linePieces[1]);
                    int vertex2ID = Integer.parseInt(linePieces[2]);
                    int splitID = -1;
                    for (int i = 3; i < linePieces.length; i++) {
                        String[] flagToNumber = linePieces[i].split("=");
                        if (flagToNumber.length == 2 && flagToNumber[0].equals("s")) {
                            splitID = Integer.parseInt(flagToNumber[1]);
                        }
                    }
                    if (splitID == -1) {
                        throw new NoSuchFieldException("Split Id for edge not found");
                    }
                    Edge edge = new Edge(edgeID, vertex1ID, vertex2ID, splitID);
                    idToEdge.put(edgeID, edge);
                }
                else if (networkMode.equals(NetworkMode.VERTEX)) {
                    line = line.replace(",", "");
                    String[] linePieces = line.split(" ");
                    int vertexID = Integer.parseInt(linePieces[0]);
                    double vertexPosX = Double.parseDouble(linePieces[1]);
                    double vertexPosY = Double.parseDouble(linePieces[2]);
                    Vertex vertex = new Vertex(vertexID, vertexPosX, vertexPosY);
                    idToVertex.put(vertexID, vertex);
                }
                else if (networkMode.equals(NetworkMode.LABEL)) {
                    String[] linePieces = line.split("'");
                    int labelID = Integer.parseInt(linePieces[0].trim());
                    String labelText = linePieces[1];
                    linePieces = linePieces[2].split(" ");
                    double labelPosX = Double.parseDouble(linePieces[1].split("=")[1].replace(",", "")); // TODO: improve that
                    double labelPosY = Double.parseDouble(linePieces[2].split("=")[1].replace(",", ""));
                    Vlabel vlabel = new Vlabel(labelID, labelText, labelPosX, labelPosY);
                    idToLabel.put(labelID, vlabel);
                }
            }
            else if (blockMode.equals(BlockMode.SPLIT)) {
                // Determin the splits data position
                splitMode = determineSplitMode(line, splitMode);
                if (splitMode.equals(SplitMode.MATRIXswitch)) {
                    line = br.readLine();
                    continue;
                }

                if (splitMode.equals(SplitMode.MATRIX)) {
                    String[] linePieces = line.split("\t");
                    String[] idAndSize = linePieces[0].split(",");
                    int splitID = Integer.parseInt(idAndSize[0].substring(1));
                    int splitSize = Integer.parseInt(idAndSize[1].trim().substring(5, idAndSize[1].trim().length() - 1));
                    double splitWeight;
                    try {
                        splitWeight = Double.parseDouble(linePieces[1].trim());
                    } catch (Exception e) {
                        System.err.println("Split weight could not be parsed. Setting it to 0");
                        splitWeight = 0;
                    }
                    String[] splitIDs = linePieces[linePieces.length-1].trim().replace(",", "").split(" ");
                    Set<Integer> splitSet = new HashSet<>();
                    for (String sID : splitIDs) {
                        splitSet.add(Integer.parseInt(sID));
                    }
                    Split split = new Split(splitID, splitSize, splitWeight, splitSet);
                    idToSplit.put(splitID, split);
                }
            }

            line = br.readLine();
        }

        nexusNetwork.addData(idToVertex, idToEdge, idToLabel, idToSplit);
        return nexusNetwork;
    }

    private static SplitMode determineSplitMode(String line, SplitMode splitMode) {
        if (line.contains("MATRIX")) {
            splitMode = SplitMode.MATRIXswitch;
        }
        else if (line.contains(";")) {
            splitMode = SplitMode.NONE;
        }
        else if (splitMode.equals(SplitMode.MATRIXswitch)) {
            splitMode = SplitMode.MATRIX;
        }
        return splitMode;
    }

    private static BlockMode determineBlockMode(String line, BlockMode blockMode) {
        if (line.contains("BEGIN Network")) {
            blockMode = BlockMode.NETWORKswitch;
        }
        else if (line.contains("BEGIN Splits")) {
            blockMode = BlockMode.SPLITswitch;
        }
        else if (line.contains("END;")) {
            blockMode = BlockMode.NONE;
        }
        else if (blockMode.equals(BlockMode.SPLITswitch)) {
            blockMode = BlockMode.SPLIT;
        }
        else if (blockMode.equals(BlockMode.NETWORKswitch)) {
            blockMode = BlockMode.NETWORK;
        }
        return blockMode;
    }

    private static NetworkMode determineNetworkMode(String line, NetworkMode networkMode) {
        if (line.contains("VERTICES")) {
            networkMode = NetworkMode.VERTEXswitch;
        }
        else if (line.contains("VLABELS")) {
            networkMode = NetworkMode.LABELswitch;
        }
        else if (line.contains("EDGES")) {
            networkMode = NetworkMode.EDGEswitch;
        }
        else if (line.contains(";")) {
            networkMode = NetworkMode.NONE;
        }
        else if (networkMode.equals(NetworkMode.VERTEXswitch)) {
            networkMode = NetworkMode.VERTEX;
        }
        else if (networkMode.equals(NetworkMode.EDGEswitch)) {
            networkMode = NetworkMode.EDGE;
        }
        else if (networkMode.equals(NetworkMode.LABELswitch)) {
            networkMode = NetworkMode.LABEL;
        }

        return networkMode;
    }
}
