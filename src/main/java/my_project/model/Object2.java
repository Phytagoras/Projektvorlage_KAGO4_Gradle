package my_project.model;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Object2 extends Object {

    public Object2(double x, double y, int track) {
        super(x, y, track);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawFilledCircle(x,y,20);
        drawTool.setCurrentColor(Color.ORANGE);
    }
    @Override
    public void update(double dt){

    }
}