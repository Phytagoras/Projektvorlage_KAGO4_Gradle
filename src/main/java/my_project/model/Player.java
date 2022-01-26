package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Player extends GraphicalObject {

    private String name;
    private double speed;
    private double isMovingTimer;
    private Color color;

    public Player(double x, double y, String name){
        this.x = x;
        this.y = y;

        width = 20;

        this.name = name;
        switch ((int)(Math.random()*10)){
            case 0:
                this.color = Color.WHITE;
                break;
            case 1:
                this.color = Color.BLACK;
                break;
            case 2:
                this.color = Color.RED;
                break;
            case 3:
                this.color = Color.BLUE;
                break;
            case 4:
                this.color = Color.GREEN;
                break;
            case 5:
                this.color = Color.YELLOW;
                break;
            case 6:
                this.color = Color.ORANGE;
                break;
            case 7:
                this.color = Color.PINK;
                break;
            case 8:
                this.color = Color.CYAN;
                break;
            case 9:
                this.color = Color.GRAY;
                break;
        }

    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(color);
        drawTool.drawFilledRectangle(x,y,width, width);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(x,y,width*2/3);
        drawTool.drawRectangle(x,y,width*1/3);
    }

    @Override
    public void update(double dt){
        if(isMovingTimer>=0) {
            x = x + speed * dt;
            isMovingTimer = isMovingTimer-1*dt;
        }

    }

    public void move(){
        isMovingTimer = 1;
    }

    public void changeSpeed(double i){
        speed = speed*i;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPos(double x, double y){
            this.x = x;
            this.y = y;
    }
}
