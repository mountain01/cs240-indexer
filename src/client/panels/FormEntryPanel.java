package client.panels;

import client.models.IndexerDataModel;
import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * Created by Matt on 4/10/2015.
 */
public class FormEntryPanel extends JSplitPane implements IndexerDataModel.IndexerDataListener{
    private IndexerDataModel model;
    private JList<Integer> list;
    private List<Field> fields;
    private boolean[][] invalid;
    private FormEntryField selected;

    public FormEntryPanel(IndexerDataModel model){
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.model = model;
        model.addListener(this);
    }

    public void setBatch(Batch batch){
        this.fields = batch.getFields();
        Integer[] recordNums = new Integer[this.fields.size()];
        this.invalid = new boolean[batch.getNumRecords()][this.fields.size()];
        for(int i = 0;i<this.fields.size();i++){
            recordNums[i] = i+1;
        }

        this.list = new JList<Integer>(recordNums);

        JScrollPane listScroll = new JScrollPane(list);
        this.setLeftComponent(listScroll);

        JPanel formPanel = new JPanel(new SpringLayout());

        for(int i = 0;i<this.fields.size();i++){
            Field field = this.fields.get(i);
            String fieldName = field.getTitle();
            JLabel fieldNameLabel = new JLabel(fieldName);
            formPanel.add(fieldNameLabel);
            FormEntryField input = new FormEntryField(fieldNameLabel,i,field.getKnowndatahtml());
            input.addFocusListener(focusListener);
            formPanel.add(input);
        }
        JScrollPane formScroll = new JScrollPane(formPanel);
        this.setRightComponent(formScroll);
        this.setDividerLocation(30);
    }

    private FocusListener focusListener = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            selected = (FormEntryField) e.getSource();
            model.selectCell(list.getSelectedIndex(),selected.getIndex());
        }

        @Override
        public void focusLost(FocusEvent e) {
            FormEntryField input = (FormEntryField) e.getSource();
            model.dataChange(list.getSelectedIndex(), input.getIndex(),input.getText());
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

    private class FormEntryField extends JTextField{
        private int index;
        private String knownDataUrl;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getKnownDataUrl() {
            return knownDataUrl;
        }

        public void setKnownDataUrl(String knownDataUrl) {
            this.knownDataUrl = knownDataUrl;
        }

        public FormEntryField(JLabel label, int index, String URL){
            label.setLabelFor(this);
            this.setMinimumSize(new Dimension(40,20));
            this.setPreferredSize(new Dimension(80,20));
            this.setMaximumSize(new Dimension(200,20));
            this.setIndex(index);
            this.setKnownDataUrl(URL);
        }
    }
}
