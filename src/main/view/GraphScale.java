package main.view;

import javafx.scene.layout.Pane;

/**
 * Created by NW on 23.02.2017.
 */
public class GraphScale {

    private double scaleX;
    private double scaleY;
    private double height;
    private double width;

    public GraphScale(double scaleX, double scaleY, double height, double width) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.height = height;
        this.width = width;
    }

    /**
     * Alternative constructor only using pane
     * @param pane
     */
    public GraphScale(Pane pane){
        this.scaleX = 1.0;
        this.scaleY = 1.0;
        this.height = pane.getHeight();
        this.width = pane.getWidth();
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidthMid(){
        return width/2.0;
    }

    public double getHeightMid(){
        return height/2.0;
    }
}
