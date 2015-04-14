package client;

import client.models.IndexerDataModel;
import server.Models.Batch;
import server.Models.Field;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Matt on 4/10/2015.
 */
public class ImageViewer extends JPanel implements IndexerDataModel.IndexerDataListener {
    // zoom
    private double scale;
    // highlight
    private boolean highlighted;
    // invert\
    private  boolean inverted;
    private Batch batch;
    private Image image;
    private Image dispImage;
    private int xDragStart;
    private int yDragStart;
    private int photoX;
    private int photoY;
    private IndexerDataModel model;
    private Highlight highlight;

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

    public Batch getBatch(){return batch;}

    public ImageViewer(IndexerDataModel model){
        super(new FlowLayout(FlowLayout.CENTER));
        this.scale = .5;
        this.setBackground(new Color(128,128,128));
        this.model = model;
        model.addListener(this);
        highlight = new Highlight();
        this.addMouseWheelListener(mouseListener);
        highlighted = true;
    }

    public void setBatch(Batch batch) {
        try{
            this.batch = batch;
            highlight.setHeight(batch.getRecordHeight());
            image = ImageIO.read(new URL(batch.getImagefilepath()));
            this.addMouseListener(mouseListener);
            this.addMouseMotionListener(mouseListener);
            redraw();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeBatch(){
        this.removeAll();
        dispImage = null;
        this.paintAll(this.getGraphics());
    }

    public void zoomIn(){
        scale += .1;
        redraw();
    }
    public void zoomOut(){
        if(scale > .1){
            scale -= .1;
            redraw();
        }
    }

    public void invert(){
        inverted = !inverted;
        redraw();
    }

    public void highlight(){
        highlighted = !highlighted;
        redraw();
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

    private MouseAdapter mouseListener = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            // get current mouse position
            int x = (e.getX() - (getWidth()/2-dispImage.getWidth(null)/2))-photoX;
            int y = (e.getY() - (getHeight()/2-dispImage.getHeight(null)/2))-photoY;

            // get row num
            int row = (int) ((y-batch.getFirstycoord()*scale)/(batch.getRecordHeight()*scale));

            // get col num
            int colWidth = 0;
            for(Field f:batch.getFields()){
                colWidth += f.getWidth()*scale;
            }

            int col = batch.getFields().size()-1;
            for(;col > -1;col--){
                int colLength = (int) (batch.getFields().get(col).getWidth()*scale);
                if(colWidth - colLength < x){
                    break;
                }
                colWidth -= colLength;
            }

            // update model
            model.selectCell(row,col);
        }

        @Override
        public void mouseReleased(MouseEvent e){
            xDragStart = yDragStart = 0;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            if(xDragStart != 0) {
                int deltaX = e.getX() - xDragStart;
                int deltaY = e.getY() - yDragStart;
                photoX += deltaX;
                photoY += deltaY;
                repaint();
            }
            xDragStart = e.getX();
            yDragStart = e.getY();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
            if(e.getPreciseWheelRotation() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        }
    };

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dispImage != null){
            g.drawImage(dispImage, photoX + (this.getWidth() / 2 - dispImage.getWidth(null) / 2),
                    photoY + (this.getHeight() / 2 - dispImage.getHeight(null) / 2), null);
            g.setColor(new Color(173,216,230,128));
            int x = photoX + (this.getWidth()/2 - dispImage.getWidth(null)/2);
            int y = photoY + (this.getHeight()/2 - dispImage.getHeight(null)/2);
            if(highlighted){
                g.fillRect((int) (x+highlight.getX()*scale),(int) (y+highlight.getY()*scale),
                        (int) (highlight.getWidth()*scale),(int) (highlight.getHeight()*scale));
            }
        }
    }

    @Override
    public void dataChange(int row, int col, String data) {

    }

    @Override
    public void selectData(int row, int col) {
        // when something is selected move hightlight to that spot
        highlight.setY(batch.getFirstycoord()+(row*batch.getRecordHeight()));
        highlight.setX(batch.getFields().get(col).getXcoord());
        highlight.setWidth(batch.getFields().get(col).getWidth());
        redraw();
    }

    private class Highlight {
        private int x;
        private int y;
        private int width;
        private int height;
        public int getX(){
            return x;
        }
        public void setX(int x){
            this.x = x;
        }
        public int getY(){
            return y;
        }
        public void setY(int y){
            this.y = y;
        }
        public int getWidth(){
            return width;
        }
        public void setWidth(int width){
            this.width = width;
        }
        public int getHeight(){
            return height;
        }
        public void setHeight(int height){
            this.height = height;
        }
    }
}
