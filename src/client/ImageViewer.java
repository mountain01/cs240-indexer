package client;

import javax.swing.*;
import java.awt.*;

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
        this.scale = 1.0;
        this.setBackground(new Color(128,128,128));
    }
}
