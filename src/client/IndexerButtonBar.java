package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == zoomInButton){

            } else if(e.getSource() == zoomOutButton){

            } else if(e.getSource() == invertButton){

            } else if(e.getSource() == toggleHighLightButton){

            } else if(e.getSource() == saveButton){

            } else if(e.getSource() == submitButton){

            }
        }
    };

    public IndexerButtonBar(){
        super(new FlowLayout(FlowLayout.LEFT));

        generateButtons();
    }

    private void  generateButtons(){
        generateButton(this, zoomInButton,"Zoom In");
        generateButton(this, zoomOutButton, "Zoom Out");
        generateButton(this, invertButton, "Invert Image");
        generateButton(this, toggleHighLightButton, "Toggle Highlights");
        generateButton(this, saveButton, "Save");
        generateButton(this, submitButton, "Submit");

        setEnabled(false);
    }

    private void generateButton(IndexerButtonBar indexerButtonBar, JButton button, String text) {
        button = new JButton(text);
        button.addActionListener(buttonListener);
        this.add(button, BorderLayout.WEST);
        button.setEnabled(false);
    }
}
