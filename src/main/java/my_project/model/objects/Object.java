package my_project.model.objects;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.model.Player;

public abstract class Object extends GraphicalObject {

    protected int track;

    public Object(double x, double y, int track){
        this.x = x;
        this.y = y;
        this.track = track;
    }

    @Override
    public abstract void draw(DrawTool drawTool);

    @Override
    public abstract void update(double dt);

    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public int getTrack(){
        return track;
    }

    public abstract void doAction(Player player);
}
