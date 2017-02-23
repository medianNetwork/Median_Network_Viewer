package main.model.networkData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Christopher Juerges on 21/09/16.
 */
public class Vlabel {
    private int labelId;
    private double posX;
    private double posY;
    private DoubleProperty posXProperty;
    private DoubleProperty posYProperty;
    private StringProperty textProperty;

    private Vlabel(int id, String text) {
        textProperty = new SimpleStringProperty(text);
        posXProperty = new SimpleDoubleProperty();
        posYProperty = new SimpleDoubleProperty();
        this.labelId = id;
        addListenerForTestingPurposes();
    }

    private void addListenerForTestingPurposes() {
        textProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("The label text has changed in the model from " + oldValue + " to " + newValue + ".");
        });
        posXProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("The posXProperty of the label has changed in the model from " + oldValue + " to " + newValue + ".");
        });
        posYProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("The posYProperty of the label has changed in the model from " + oldValue + " to " + newValue + ".");
        });
    }

    public Vlabel(int id, String text, double posX, double posY) {
        this(id, text);
        this.posX = posX;
        this.posY = posY;
        posXProperty.setValue(posX);
        posYProperty.setValue(posY);
    }

    public double getPosX() {
        return posXProperty.get();
    }

    public double getPosY() {
        return posYProperty.get();
    }

    public StringProperty getTextProperty() {
        return textProperty;
    }

    public DoubleProperty getPosXProperty() {
        return posXProperty;
    }

    public DoubleProperty getPosYProperty() {
        return posYProperty;
    }

    public String getText() {
        return textProperty.get();
    }

    public int getId() {
        return labelId;
    }
}
