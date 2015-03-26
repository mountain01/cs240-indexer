package search.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 3/26/2015.
 */
public class SearchPanel extends JPanel {
    
    JTextField valuesField;
    JButton searchButton;

    public String getValuesField() {
        return valuesField.getText();
    }

    SearchPanel(){
        super();
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        add(Box.createRigidArea(new Dimension(10,0)));
        add(new JLabel("VALUES: "));
        add(Box.createRigidArea(new Dimension(10,0)));

        valuesField = new JTextField(10);
        valuesField.setMinimumSize(valuesField.getPreferredSize());
        valuesField.setMaximumSize(valuesField.getPreferredSize());
        add(valuesField);
        add(Box.createRigidArea(new Dimension(15,0)));

        searchButton = new JButton("SEARCH");
        add(searchButton);
        add(Box.createRigidArea(new Dimension(10,0)));

        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
    }
}
