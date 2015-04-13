package client.popups;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Matt on 4/13/2015.
 */
public class ViewSamplePopup extends JDialog {
    public ViewSamplePopup(JFrame parent, String title, String url){
        super(parent,title,JDialog.DEFAULT_MODALITY_TYPE);
        try{
            this.setResizable(false);
            ImageIcon icon = new ImageIcon(redraw(ImageIO.read(new URL(url))));

            this.setSize(icon.getIconWidth(),icon.getIconHeight()+25);
            this.add(new JLabel(icon), BorderLayout.CENTER);

            JButton cancelButton = new JButton("Close");
            cancelButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    cancel();
                }
            });
            JPanel panel = new JPanel();
            panel.add(cancelButton);
            this.add(panel, BorderLayout.SOUTH);

            this.setLocationRelativeTo(null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private BufferedImage redraw(BufferedImage image) {
        double zoomLevel = 0.5;
        int newImageWidth = (int) (image.getWidth() * zoomLevel);
        int newImageHeight = (int) (image.getHeight() * zoomLevel);
        BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newImageWidth , newImageHeight , null);
        g.dispose();
        return resizedImage;
    }

    private void cancel() {
        this.setVisible(false);
    }
}
