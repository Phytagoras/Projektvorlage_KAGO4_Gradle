package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinnerText extends GraphicalObject {

    private String playerName;
    private int textSize;

    public WinnerText(String playerName) {
        this.playerName = playerName;

        this.x = (double) Config.WINDOW_WIDTH*1/6;
        this.y = (double) Config.WINDOW_HEIGHT*1/6;

        this.width = (double) Config.WINDOW_WIDTH*2/3;
        this.height = (double) Config.WINDOW_HEIGHT*2/3;

        textSize = (int) width/10;
    }

    @Override
    public void draw(DrawTool drawTool) {
        BufferedImage bufferedImage = null;
        try {
            // Create a buffered image with transparency
            bufferedImage = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bufferedImage.createGraphics();

            drawTool.setCurrentColor(Color.WHITE);
            Image img = ImageIO.read(new File("src/main/resources/graphic/gameOver.png")).getScaledInstance((int)width, (int)height*4/6, java.awt.Image.SCALE_SMOOTH);
            bGr.drawImage(img, 0, 0, null);

            bGr.setColor(Color.BLACK);
            bGr.setFont(new Font("TimesRoman", Font.BOLD, textSize));
            drawCenteredString(playerName+" won!!!", (int)width, (int)height, bGr);
            bGr.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawTool.drawImage(bufferedImage, x, y);
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) *5/6);
        g.drawString(s, x, y);
    }

    @Override
    public void update(double dt){

    }
}
