package client;

import client.menus.IndexMenu;
import client.menus.IndexerButtonBar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 4/9/2015.
 */
public class IndexerFrame extends JFrame {

    // menu
    private IndexMenu menuBar;
    // button bar
    private IndexerButtonBar buttons;
    // picture spot
    private ImageViewer imageViewer;
    // bottom section
    private IndexerFooter footer;

     public IndexerFrame(ImageViewer imageViewwer){
         super("Indexer");
         setSize(1000,700);
         setLocation(500,500);
         setResizable(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         menuBar = new IndexMenu();
         setJMenuBar(menuBar);

         buttons = new IndexerButtonBar();
         add(buttons, BorderLayout.NORTH);

         imageViewer = new ImageViewer();

         footer = new IndexerFooter();
         JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,imageViewer,footer);
         splitPane.setDividerLocation(400);
         this.add(splitPane, BorderLayout.CENTER);
     }
}
