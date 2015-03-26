package search.views;

import server.Models.Field;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Matt on 3/25/2015.
 */
public class ProjectFieldsPanel extends JPanel {

    public ProjectFieldsPanel(List<ProjectFields> fieldList){
        super();

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int i=0;
        for(ProjectFields pf:fieldList){
            c.gridx = i;
            c.gridy = 0;
            JLabel label = new JLabel(pf.getProject().getTitle());
            this.add(label,c);
            int j = 1;
            for(Field f:pf.getFields()){
                JCheckBox check = new JCheckBox(f.getTitle());
                c.gridx = i;
                c.gridy = j++;
                this.add(check,c);
            }
            i++;
            add(Box.createRigidArea(new Dimension(10,0)));
        }
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
    }
}
