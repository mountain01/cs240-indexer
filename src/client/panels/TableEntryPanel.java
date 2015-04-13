package client.panels;

import client.models.IndexerDataModel;
import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/10/2015.
 */
public class TableEntryPanel extends JPanel implements IndexerDataModel.IndexerDataListener{
    private String[] columnNames;
    private List<Field> fields;
    private Object[][] tableInfo;
    private boolean[][] invalid;
    private IndexerDataModel model;
    private JTable table;

    public TableEntryPanel(IndexerDataModel model){
        super(new BorderLayout());
        this.model = model;
        model.addListener(this);
        this.fields = new ArrayList<Field>();
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

        table = new JTable(tableInfo,columnNames);

        table.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(selectionListener);


        this.add(table.getTableHeader(), BorderLayout.PAGE_START);
        this.add(table,BorderLayout.CENTER);

    }

    private ListSelectionListener selectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                model.selectCell(table.getSelectedRow(),table.getSelectedColumn()-1);
            }
        }
    };

    public void removeBatch() {
        this.removeAll();
    }

    @Override
    public void dataChange(int row, int col, String data) {

    }

    @Override
    public void selectData(int row, int col) {

    }
}
