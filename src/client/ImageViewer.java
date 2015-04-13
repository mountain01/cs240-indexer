package client;

import server.Models.Batch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Matt on 4/10/2015.
 */
public class ImageViewer extends JPanel {
    // zoom
    private double scale;
    // highlight
    private boolean highlighted;
    // invert\
    private  boolean inverted;
    private Batch batch;
    private Image image;
    private Image dispImage;

    public void setHighlight(boolean val){
        highlighted = val;
    }

    public boolean isHighlighted(){
        return highlighted;
    }

    public void setInverted(boolean val){
        inverted = val;
    }

    public boolean isInverted(){
        return inverted;
    }

    public double getScale(){
        return scale;
    }

    public ImageViewer(){
        super(new FlowLayout(FlowLayout.CENTER));
        this.scale = .5;
        this.setBackground(new Color(128,128,128));
    }

    public void setBatch(Batch batch) {
        try{
            this.batch = batch;
            //highlight.setHeight(batch.getRecordHeight());
            image = ImageIO.read(new URL(batch.getImagefilepath()));
            redraw();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void redraw(){
        if(batch == null)
            return;
        int newImageWidth = (int) (image.getWidth(null) * scale);
        int newImageHeight = (int) (image.getHeight(null) * scale);
        dispImage = new BufferedImage(newImageWidth , newImageHeight, ((BufferedImage) image).getType());
        Graphics2D g = ((BufferedImage) dispImage).createGraphics();
        g.drawImage(image, 0, 0, newImageWidth , newImageHeight , null);
        g.dispose();
        if(inverted){
            RescaleOp op = new RescaleOp(-1.0f, 255f, null);
            dispImage = op.filter((BufferedImage) dispImage, null);
        }
        paintComponent(getGraphics());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dispImage != null){
            int photoX = 0;
            int photoY = 0;
            g.drawImage(dispImage, photoX + (this.getWidth()/2 - dispImage.getWidth(null)/2),
                    photoY + (this.getHeight()/2 - dispImage.getHeight(null)/2), null);
            g.setColor(new Color(173,216,230,128));
            int x = photoX + (this.getWidth()/2 - dispImage.getWidth(null)/2);
            int y = photoY + (this.getHeight()/2 - dispImage.getHeight(null)/2);
//            if(highlighted){
//                g.fillRect((int) (x+highlight.getX()*scale),(int) (y+highlight.getY()*scale),
//                        (int) (highlight.getWidth()*scale),(int) (highlight.getHeight()*scale));
//            }
        }
    }
}
