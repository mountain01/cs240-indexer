package client;

import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import java.util.List;

/**
 * Created by Matt on 4/12/2015.
 */
public class FieldHelpPanel extends JEditorPane {

    List<Field> fields;

    public FieldHelpPanel(){
        super();
        this.setContentType("text/html");
    }

    public void setBatch(Batch batch){
        fields = batch.getFields();
    }

    public void removeBatch() {
        fields = null;
        this.removeAll();
        this.setText("");
    }
}
