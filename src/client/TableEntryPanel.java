package client;

import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Matt on 4/10/2015.
 */
public class TableEntryPanel extends JPanel {
    private String[] columnNames;
    private List<Field> fields;
    private Object[][] tableInfo;
    private boolean[][] invalid;

    public TableEntryPanel(){
        super(new BorderLayout());
    }

    public void setBatch(Batch batch){
        this.fields = batch.getFields();

        columnNames = new String[this.fields.size()+1];
        columnNames[0] = "Record Number";
        for(int i = 0;i<this.fields.size();i++){
            columnNames[i+1] = this.fields.get(i).getTitle();
        }

        tableInfo = new String[batch.getNumRecords()][this.fields.size()+1];
        invalid = new boolean[batch.getNumRecords()][this.fields.size()+1];
        // put record numbers in first column
        for(int i = 0;i<batch.getNumRecords();i++){
            tableInfo[i][0] = Integer.toString(i+1);
        }

        JTable table = new JTable(tableInfo,columnNames);

        this.add(table.getTableHeader(), BorderLayout.PAGE_START);
        this.add(table,BorderLayout.CENTER);

    }

    public void removeBatch() {
        this.removeAll();
    }
}
