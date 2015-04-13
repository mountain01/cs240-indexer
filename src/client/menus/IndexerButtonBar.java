package client.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/9/2015.
 */
public class IndexerButtonBar extends JPanel {

    // zoom in/out
    private JButton zoomInButton;
    private JButton zoomOutButton;
    // invert
    private JButton invertButton;
    // highlights
    private JButton toggleHighLightButton;
    // save
    private JButton saveButton;
    // submit
    private JButton submitButton;
    private List<ButtonBarListener> listeners;

    public void addListener(ButtonBarListener lisenter){
        this.listeners.add(lisenter);
    }

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == zoomInButton){
                for(ButtonBarListener listener:listeners){
                    listener.zoomIn();
                }
            } else if(e.getSource() == zoomOutButton){
                for(ButtonBarListener listener:listeners){
                    listener.zoomOut();
                }
            } else if(e.getSource() == invertButton){
                for(ButtonBarListener listener:listeners){
                    listener.invertImage();
                }
            } else if(e.getSource() == toggleHighLightButton){
                for(ButtonBarListener listener:listeners){
                    listener.toggleHighlight();
                }
            } else if(e.getSource() == saveButton){
                for(ButtonBarListener listener:listeners){
                    listener.save();
                }
            } else if(e.getSource() == submitButton){
                for(ButtonBarListener listener:listeners){
                    listener.submit();
                }
            }
        }
    };

    public IndexerButtonBar(){
        super(new FlowLayout(FlowLayout.LEFT));
        listeners = new ArrayList<ButtonBarListener>();

        generateButtons();
    }

    public void setEnabled(boolean enabled){
        zoomInButton.setEnabled(enabled);
        zoomOutButton.setEnabled(enabled);
        invertButton.setEnabled(enabled);
        toggleHighLightButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);
        submitButton.setEnabled(enabled);
    }

    private void  generateButtons(){
        zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(buttonListener);
        this.add(zoomInButton,BorderLayout.WEST);


        zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(buttonListener);
        this.add(zoomOutButton,BorderLayout.WEST);


        invertButton = new JButton("Invert Image");
        invertButton.addActionListener(buttonListener);
        this.add(invertButton,BorderLayout.WEST);


        toggleHighLightButton = new JButton("Toggle Highlights");
        toggleHighLightButton.addActionListener(buttonListener);
        this.add(toggleHighLightButton,BorderLayout.WEST);


        saveButton = new JButton("Save");
        saveButton.addActionListener(buttonListener);
        this.add(saveButton,BorderLayout.WEST);


        submitButton = new JButton("Submit");
        submitButton.addActionListener(buttonListener);
        this.add(submitButton,BorderLayout.WEST);

        setEnabled(true);
    }

    public interface ButtonBarListener{

        public void zoomIn();
        public void zoomOut();
        public void invertImage();
        public void toggleHighlight();
        public void save();
        public void submit();
    }
}
