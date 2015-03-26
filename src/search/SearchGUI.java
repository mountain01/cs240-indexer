package search;

import search.views.SearchGUIFrame;

import java.awt.*;

/**
 * Created by Matt on 3/25/2015.
 */
public class SearchGUI {

    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
                    public void run() {
                        SearchGUIFrame frame = new SearchGUIFrame();
                        frame.setVisible(true);
                    }
                }
        );
    }
}
