package client.panels;

import org.omg.CORBA.INTERNAL;
import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Matt on 4/10/2015.
 */
public class FormEntryPanel extends JSplitPane{
    private JList<Integer> list;
    private List<Field> fields;
    private boolean[][] invalid;

    public FormEntryPanel(){
        super(JSplitPane.HORIZONTAL_SPLIT);
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
            formPanel.add(input);
        }
        JScrollPane formScroll = new JScrollPane(formPanel);
        this.setRightComponent(formScroll);
        this.setDividerLocation(30);


    }

    public void removeBatch() {
        this.removeAll();
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
