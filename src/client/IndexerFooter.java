package client;

import client.models.IndexerDataModel;
import client.models.QualityCheckModel;
import client.panels.FieldHelpPanel;
import client.panels.FormEntryPanel;
import client.panels.ImageNavPanel;
import client.panels.TableEntryPanel;
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

    public IndexerFooter(IndexerDataModel model, QualityCheckModel qCheck){
        super(JSplitPane.HORIZONTAL_SPLIT);
        generateComponents(model,qCheck);
    }

    private void generateComponents(IndexerDataModel model, QualityCheckModel qCheck) {
        JTabbedPane leftSide = new JTabbedPane();
        tablePanel = new TableEntryPanel(model,qCheck);
        JScrollPane tableScroll = new JScrollPane(tablePanel);
        formPanel = new FormEntryPanel(model,qCheck);
        leftSide.addTab("Table Entry",tableScroll);
        leftSide.addTab("Form Entry",formPanel);

        JTabbedPane rightSide = new JTabbedPane();
        helpPanel = new FieldHelpPanel(model);
        JScrollPane helpScroll = new JScrollPane(helpPanel);
        navPanel = new ImageNavPanel(model);
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

    public Object[][] getRecordValues(){
        return formPanel.getRecordValues();
    }
}
