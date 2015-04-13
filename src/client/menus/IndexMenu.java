package client.menus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
    private List<IndexMenuListener> listeners;

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == downloadBatchMenuItem){
                for(IndexMenuListener listener:listeners){
                    listener.requestBatch();
                }
            } else if(e.getSource() == logoutMenuItem){
                for(IndexMenuListener listener:listeners){
                    listener.logOut();
                }
            } else if(e.getSource() == exitMenuItem){
                for(IndexMenuListener listener:listeners){
                    listener.exit();
                }
            }
        }
    };

    public void addListener(IndexMenuListener listener){
        this.listeners.add(listener);
    }

    public IndexMenu(){
        listeners = new ArrayList<IndexMenuListener>();
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

    public interface IndexMenuListener{
        public void exit();
        public void logOut();
        public void requestBatch();
    }
}
