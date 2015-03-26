package search.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 3/26/2015.
 */
public class ImagesPanel extends JPanel {
    JComboBox imageList;
    JButton viewButton;
    ImagesPanel(){
        super();
        setLayout(new FlowLayout());

        imageList = new JComboBox<String>();
        add(imageList);

        add(Box.createRigidArea(new Dimension(10,0)));
        viewButton = new JButton("VIEW");
        add(viewButton);
        add(Box.createRigidArea(new Dimension(10,0)));

        setPreferredSize(new Dimension(500,50));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
    }
}
