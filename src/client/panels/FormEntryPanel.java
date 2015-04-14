package client.panels;

import client.models.IndexerDataModel;
import client.models.QualityCheckModel;
import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.ObjectView;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/10/2015.
 */
public class FormEntryPanel extends JSplitPane implements IndexerDataModel.IndexerDataListener, QualityCheckModel.QualityCheckerListener {
    private IndexerDataModel model;
    private JList<Integer> list;
    private List<Field> fields;
    private boolean[][] invalid;
    private FormEntryField selected;
    private Object[][] data;
    private List<FormEntryField> inputs;
    private QualityCheckModel qCheck;

    public FormEntryPanel(IndexerDataModel model, QualityCheckModel qCheck){
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.model = model;
        this.qCheck = qCheck;
        qCheck.addListener(this);
        model.addListener(this);
        inputs = new ArrayList<FormEntryField>();
    }

    public Object[][] getRecordValues(){return data;}

    public void setBatch(Batch batch){
        this.fields = batch.getFields();
        Integer[] recordNums = new Integer[batch.getNumRecords()];
        this.invalid = new boolean[batch.getNumRecords()][this.fields.size()];
        this.data = new String[batch.getNumRecords()][this.fields.size()];
        for(int i = 0;i<batch.getNumRecords();i++){
            recordNums[i] = i+1;
        }

        this.list = new JList<Integer>(recordNums);
        list.addListSelectionListener(selectionListenter);

        JScrollPane listScroll = new JScrollPane(list);
        this.setLeftComponent(listScroll);

        JPanel formPanel = new JPanel(new GridLayout(0,2));

        for(int i = 0;i<this.fields.size();i++){
            Field field = this.fields.get(i);
            String fieldName = field.getTitle();
            JLabel fieldNameLabel = new JLabel(fieldName);
            formPanel.add(fieldNameLabel);
            FormEntryField input = new FormEntryField(fieldNameLabel,i,field.getKnowndatahtml());
            input.addFocusListener(focusListener);
            formPanel.add(input);
            inputs.add(input);
        }
        JScrollPane formScroll = new JScrollPane(formPanel);
        this.setRightComponent(formScroll);
        this.setDividerLocation(50);
    }

    private ListSelectionListener selectionListenter = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting() && list != null && selected != null){
                model.selectCell(list.getSelectedIndex(),selected.getIndex());
            }
            updateFields();
        }
    };

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
            updateFields();
        }
    };
    private MouseAdapter mouseAdapter = new MouseAdapter(){
        public void mouseClicked (MouseEvent e) {
            if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
                final int row = list.getSelectedIndex();
                final int col = ((FormEntryField) e.getComponent()).getIndex();
                final String knownData = ((FormEntryField) e.getComponent()).getKnownDataUrl();
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("See Suggestions");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        qCheck.getSuggestions(row,col,knownData,(String) data[row][col]);
                    }
                });
                popup.add(menuItem);
                popup.show(e.getComponent(),e.getX(), e.getY());
            }
        }
    };

    public void removeBatch() {
        this.removeAll();
    }

    @Override
    public void dataChange(int row, int col, String data) {
        this.data[row][col] = data;
        qCheck.fieldChange(this.fields.get(col).getKnowndatahtml());
        qCheck.isInvalidEntry(row,col,data);
        updateFields();
    }

    @Override
    public void selectData(int row, int col) {
        list.setSelectedIndex(row);
        inputs.get(col).requestFocus();
        updateFields();
    }

    private void updateFields(){
        for(FormEntryField input : inputs){
            int row = list.getSelectedIndex();
            int col = input.getIndex();
            String value = (String) data[row][col];
            if(data != null)
                input.setText(value);
            if(invalid[list.getSelectedIndex()][input.getIndex()]) {
                input.setBackground(new Color(0xFF0000));
                input.addMouseListener(mouseAdapter);
            }else{
                input.setBackground(Color.WHITE);
                if(input.getMouseListeners().length > 0)
                    input.removeMouseListener(mouseAdapter);
            }
        }
    }

    @Override
    public void setInvalid(int row, int col, boolean invalid) {
        this.invalid[row][col] = invalid;
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
