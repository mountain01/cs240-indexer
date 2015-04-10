package client;

import search.views.SearchGUIFrame;

import java.awt.*;

/**
 * Created by Matt on 4/8/2015.
 */
public class IndexerGUI {

    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
                    public void run() {
                        IndexerFrame frame = new IndexerFrame();
                        frame.setVisible(true);
                    }
                }
        );
    }
}
