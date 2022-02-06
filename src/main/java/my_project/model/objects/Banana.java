package my_project.model.objects;

import KAGO_framework.view.DrawTool;
import my_project.model.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Banana extends Object {

    public Banana(double x, double y, int track) {
        super(x, y, track);

        width = 25;
        height = 25;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.WHITE);
        BufferedImage bufferedImage = null;
        try {
            Image img = ImageIO.read(new File("src/main/resources/graphic/banana.png")).getScaledInstance((int)width, (int)height, java.awt.Image.SCALE_SMOOTH);

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

    }

    @Override
    public void doAction(Player player) {
        player.stun();
    }
}
