package my_project.model.objects;

import KAGO_framework.view.DrawTool;
import my_project.model.Player;

import java.awt.*;

public class Portal extends Object {

    public Portal(double x, double y, int track) {
        super(x, y, track);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.ORANGE);
        drawTool.drawFilledCircle(x,y,20);
    }
    @Override
    public void update(double dt){

    }

    @Override
    public void doAction(Player player) {
        player.setX(0);
    }
}