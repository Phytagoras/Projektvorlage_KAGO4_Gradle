package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends GraphicalObject {

    private static final int minSpeed = 1;
    private static final int maxSpeed = 100;

    private int id;
    private String name;
    private double speed;
    private double isMovingTimer;
    private double stunTimer;

    private Color color;

    public Player(int id, double x, double y, String name){
        this.id = id;
        this.x = x;
        this.y = y;

        speed = 25;
        width = 30;
        height = 30;

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
        drawTool.drawText(x, y, name);

        drawTool.setCurrentColor(Color.WHITE);
        BufferedImage bufferedImage = null;
        try {
            Image img = ImageIO.read(new File("src/main/resources/graphic/spaceship.png")).getScaledInstance((int)width, (int)height, java.awt.Image.SCALE_SMOOTH);

            // Create a buffered image with transparency
            bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bufferedImage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawTool.drawImage(bufferedImage, x, y);
    }

    @Override
    public void update(double dt){
        if(isMovingTimer>=0 && stunTimer <= 0) {
            x = x + speed * dt;
        }
        if(stunTimer > 0){
            stunTimer -= 1*dt;
        }
        isMovingTimer = isMovingTimer-1*dt;
    }

    public void move(){
        isMovingTimer = 2;
    }

    public void changeSpeed(double i){
        speed = speed*i;
        if(speed > maxSpeed){
            speed = maxSpeed;
        }else if(speed <minSpeed){
            speed = minSpeed;
        }
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPos(double x, double y){
            this.x = x;
            this.y = y;
    }

    public void stun(){
        stunTimer = 3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
