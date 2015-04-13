package client;

import server.Models.Batch;

import javax.swing.*;

/**
 * Created by Matt on 4/10/2015.
 */
public class IndexerFooter extends JSplitPane {
    //entry spot
        // table entry
    private TableEntryPanel tablePanel;
        // form entry
    private FormEntryPanel formPanel;
    // field help/other
        // field help
    private FieldHelpPanel helpPanel;
        // image nav
    private ImageNavPanel navPanel;

    public IndexerFooter(){
        super(JSplitPane.HORIZONTAL_SPLIT);
        generateComponents();
    }

    private void generateComponents() {
        JTabbedPane leftSide = new JTabbedPane();
        tablePanel = new TableEntryPanel();
        JScrollPane tableScroll = new JScrollPane(tablePanel);
        formPanel = new FormEntryPanel();
        leftSide.addTab("Table Entry",tableScroll);
        leftSide.addTab("Form Entry",formPanel);

        JTabbedPane rightSide = new JTabbedPane();
        helpPanel = new FieldHelpPanel();
        JScrollPane helpScroll = new JScrollPane(helpPanel);
        navPanel = new ImageNavPanel();
        rightSide.addTab("Field Help",helpScroll);
        rightSide.addTab("Image Navigation", navPanel);

        this.setLeftComponent(leftSide);
        this.setRightComponent(rightSide);
        this.setDividerLocation(300);
    }

    public void setBatch(Batch batch){
        tablePanel.setBatch(batch);
        formPanel.setBatch(batch);
        helpPanel.setBatch(batch);
        navPanel.setBatch(batch);
    }

    public void removeBatch(){
        tablePanel.removeBatch();
        formPanel.removeBatch();
        helpPanel.removeBatch();
        navPanel.removeBatch();
    }
}
