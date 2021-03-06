package client.panels;

import client.models.IndexerDataModel;
import client.models.QualityCheckModel;
import server.Models.Batch;
import server.Models.Field;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/10/2015.
 */
public class TableEntryPanel extends JPanel implements IndexerDataModel.IndexerDataListener, QualityCheckModel.QualityCheckerListener {
    private String[] columnNames;
    private List<Field> fields;
    private Object[][] tableInfo;
    private boolean[][] invalid;
    private IndexerDataModel model;
    private JTable table;
    private QualityCheckModel qCheck;

    public TableEntryPanel(IndexerDataModel model, QualityCheckModel qCheck){
        super(new BorderLayout());
        this.model = model;
        this.qCheck = qCheck;
        model.addListener(this);
        qCheck.addListener(this);
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
        table.addMouseListener(mouseAdapter);

        table.setCellSelectionEnabled(true);
        table.setModel(tableModel);
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(selectionListener);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 1; i < tableModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setCellRenderer(renderer);
        }
        columnModel.getSelectionModel().addListSelectionListener(selectionListener);

        this.add(table.getTableHeader(), BorderLayout.PAGE_START);
        this.add(table,BorderLayout.CENTER);

    }

    private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(invalid == null)
                return c;
            if(invalid[row][column]){
                if(isSelected)
                    c.setBackground(new Color(255,57,50,128));
                else
                    c.setBackground(new Color(0xFF0000));
            }else{
                if(isSelected)
                    c.setBackground(new Color(173,216,230,225));
                else
                    c.setBackground(new Color(0xFFFFFF));
            }
            return c;
        }
    };

    private AbstractTableModel tableModel = new AbstractTableModel(){
        private static final long serialVersionUID = 1L;

        @Override
        public Object getValueAt(int arg0, int arg1){
            return tableInfo[arg0][arg1];
        }

        @Override
        public int getRowCount(){
            return tableInfo.length;
        }

        @Override
        public int getColumnCount(){
            return columnNames.length;
        }

        @Override
        public boolean isCellEditable(int row, int col){
            return col >= 1;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            tableInfo[row][col] = value;
            model.dataChange(row,col-1,(String) value);
            fireTableCellUpdated(row, col);
        }
    };

    private ListSelectionListener selectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                model.selectCell(table.getSelectedRow(),table.getSelectedColumn()-1);
            }
        }
    };

    private MouseAdapter mouseAdapter = new MouseAdapter(){
        public void mouseClicked (MouseEvent e) {
            final int row = table.rowAtPoint(e.getPoint());
            final int col = table.columnAtPoint(e.getPoint());
            if(e.getButton() == MouseEvent.BUTTON3 && invalid[row][col]){
                final String knownData = fields.get(col-1).getKnowndatahtml();
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("See Suggestions");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        qCheck.getSuggestions(row,col-1,knownData,(String) tableInfo[row][col]);
                    }
                });
                popup.add(menuItem);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }else if(e.getButton() == MouseEvent.BUTTON1){
                model.selectCell(row,col-1);
            }
        }
    };

    public void removeBatch() {
        this.removeAll();
    }

    @Override
    public void dataChange(int row, int col, String data) {
        tableInfo[row][col+1] = data;
        qCheck.fieldChange(this.fields.get(col).getKnowndatahtml());
        qCheck.isInvalidEntry(row,col,data);
    }

    @Override
    public void selectData(int row, int col) {
        table.changeSelection(row,col+1,false,false);
    }

    @Override
    public void setInvalid(int row, int col, boolean invalid) {
        this.invalid[row][col+1] = invalid;
        table.repaint();
    }
}
