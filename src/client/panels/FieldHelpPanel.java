package client.panels;

import client.models.IndexerDataModel;
import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Matt on 4/12/2015.
 */
public class FieldHelpPanel extends JEditorPane implements IndexerDataModel.IndexerDataListener {

    private IndexerDataModel model;
    List<Field> fields;

    public FieldHelpPanel(IndexerDataModel model){
        super();
        this.setContentType("text/html");
        this.model = model;
        model.addListener(this);
    }

    public void setBatch(Batch batch){
        fields = batch.getFields();
    }

    public void removeBatch() {
        fields = null;
        this.removeAll();
        this.setText("");
    }

    @Override
    public void dataChange(int row, int col, String data) {

    }

    @Override
    public void selectData(int row, int col) {
        if(col >= 0){
            try {
                this.setPage(new URL(fields.get(col).getHelphtml()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
