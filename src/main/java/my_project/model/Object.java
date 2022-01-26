package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public abstract class Object extends GraphicalObject {

    protected int track;

    public Object(double x, double y, int track){
        this.x = x;
        this.y = y;
        this.track = track;
    }

    @Override
    public void draw(DrawTool drawTool){

    }

    @Override
    public void update(double dt){

    }
}
