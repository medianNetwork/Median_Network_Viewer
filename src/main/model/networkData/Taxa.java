package main.model.networkData;

/**
 * Created by Christopher Juerges on 26/09/16.
 */
public class Taxa {
    private int id;
    private String text;

    public Taxa(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
