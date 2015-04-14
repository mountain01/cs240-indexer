package client.popups;

import client.models.IndexerDataModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Matt on 4/14/2015.
 */
public class SeeSuggestionsPopup extends JDialog {


    private int row;
    private int col;
    private IndexerDataModel model;
    private JList<String> suggestions;
    private JButton cancelButton;
    private JButton useSuggestionButton;

    public SeeSuggestionsPopup(JFrame parent, List<String> suggestions,int row, int col, IndexerDataModel model){
        super(parent,"See Suggestions",JDialog.DEFAULT_MODALITY_TYPE);
        String[] array = new String[suggestions.size()];
        for(int i = 0;i<suggestions.size();i++){
            array[i] = suggestions.get(i);
        }
        this.suggestions = new JList<String>(array);
        this.row = row;
        this.col = col;
        this.model = model;

        createComponents();
    }

    private void createComponents() {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(windowAdapter);

        suggestions.setSize(190,100);
        suggestions.addListSelectionListener(selectionListener);
        suggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(suggestions);
        scrollPane.setSize(190, 100);
        this.add(scrollPane, BorderLayout.CENTER);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(buttonListener);

        useSuggestionButton = new JButton("Use Suggestion");
        useSuggestionButton.setEnabled(false);
        useSuggestionButton.addActionListener(buttonListener);

        Panel buttonPanel = new Panel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(useSuggestionButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
    }

    private WindowAdapter windowAdapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            setVisible(false);
        }
    };

    private ListSelectionListener selectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            useSuggestionButton.setEnabled(true);
        }
    };

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==cancelButton){
                setVisible(false);
            } else if(e.getSource()==useSuggestionButton){
                model.dataChange(row,col,suggestions.getSelectedValue().toString());
                setVisible(false);
            }
        }
    };
}
