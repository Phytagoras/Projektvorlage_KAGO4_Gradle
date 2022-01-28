package my_project.model.objects;

import KAGO_framework.view.DrawTool;
import my_project.model.Player;

import java.awt.*;

public class Banana extends Object {

    public Banana(double x, double y, int track) {
        super(x, y, track);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.YELLOW);
        drawTool.drawFilledArc(x, y, 20, 20, 9, 1);
    }
    @Override
    public void update(double dt){

    }

    @Override
    public void doAction(Player player) {
        player.stun();
    }
}
