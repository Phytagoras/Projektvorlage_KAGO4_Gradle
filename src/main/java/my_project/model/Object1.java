package my_project.model;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Object1 extends Object {

    public Object1(double x, double y, int track) {
        super(x, y, track);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawFilledArc(x, y, 20, 20, 9, 1);
        drawTool.setCurrentColor(Color.YELLOW);
    }
    @Override
    public void update(double dt){

    }
 }
