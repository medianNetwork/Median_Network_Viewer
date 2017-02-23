package main.model.networkData;



import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christopher Juerges on 26/09/16.
 */
public class Split {
    private int id;
    private int size;
    private double dist;
    private Set<Integer> splitA;
    private Set<Integer> splitB;

    public Split(int id, int size, double dist, Set<Integer> splitA) {
        this.id = id;
        this.size = size;
        this.splitA = splitA;
        this.dist = dist;
    }

    public void createSplitB(int numIDs) {
        splitB = new HashSet<>();
        for (int i = 1; i <= numIDs; i++) {
            if (!splitA.contains(i)) {
                splitB.add(i);
            }
        }
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public Set<Integer> getSplitA() {
        return splitA;
    }

    public Set<Integer> getSplitB() {
        return splitB;
    }

    public double getDist() {
        return dist;
    }
}
