package client.panels;

import client.models.IndexerDataModel;
import server.Models.Batch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Matt on 4/12/2015.
 */
public class ImageNavPanel extends JPanel implements IndexerDataModel.IndexerDataListener{

    private IndexerDataModel model;
    private Image image;
    private Image dispImage;

    public ImageNavPanel(IndexerDataModel model){
        super();
        this.setBackground(new Color(128, 128, 128));
        this.model = model;
        model.addListener(this);
    }

    public void setBatch(Batch batch) {
        try {
            image = ImageIO.read(new URL(batch.getImagefilepath()));
            drawImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawImage() {
        int newWidth = (int) (image.getWidth(null) * .3);
        int newHeight = (int) (image.getHeight(null) * .3);
        dispImage = new BufferedImage(newWidth,newHeight,((BufferedImage) image).getType());
        Graphics2D g = ((BufferedImage) dispImage).createGraphics();
        g.drawImage(image, 0, 0, newWidth , newHeight , null);
        g.dispose();
    }

    public void removeBatch() {
        this.removeAll();
        image = dispImage = null;
    }

    @Override
    public void dataChange(int row, int col, String data) {

    }

    @Override
    public void selectData(int row, int col) {

    }
}
