package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Matt on 4/9/2015.
 */
public class IndexMenu extends JMenuBar {
    // download
    private JMenuItem downloadBatchMenuItem;
    // logout
    private JMenuItem logoutMenuItem;
    // exit
    private JMenuItem exitMenuItem;

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == downloadBatchMenuItem){

            } else if(e.getSource() == logoutMenuItem){

            } else if(e.getSource() == exitMenuItem){

            }
        }
    };

    public IndexMenu(){
        JMenu menu = new JMenu("File");
        menu.setMnemonic('f');
        this.add(menu);

        downloadBatchMenuItem = new JMenuItem("Download Batch", KeyEvent.VK_D);
        downloadBatchMenuItem.addActionListener(listener);
        logoutMenuItem = new JMenuItem("Logout",KeyEvent.VK_L);
        logoutMenuItem.addActionListener(listener);
        exitMenuItem = new JMenuItem("Exit",KeyEvent.VK_E);
        exitMenuItem.addActionListener(listener);
        menu.add(downloadBatchMenuItem);
        menu.add(logoutMenuItem);
        menu.add(exitMenuItem);
    }
}
