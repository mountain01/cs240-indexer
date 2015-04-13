package client.popups;

import server.Models.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/13/2015.
 */
public class DownloadBatchPopup extends JDialog {
    private List<Project> projects;
    private String[] titles;
    private JComboBox<String> projCombo;
    private JButton viewSampleButton;
    private JButton cancelButton;
    private JButton downloadButton;
    private List<DownloadBatchPopupListener> listeners;

    public DownloadBatchPopup(JFrame parent, List<Project> projects) {
        super(parent, "Download Batch", JDialog.DEFAULT_MODALITY_TYPE);
        listeners = new ArrayList<DownloadBatchPopupListener>();
        this.setProjects(projects);
        createComponents();
    }

    public void addListener(DownloadBatchPopupListener listener){
        this.listeners.add(listener);
    }

    private void close(){this.setVisible(false);}

    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Project project = new Project();
            for(Project p:projects){
                if(p.getTitle().equals(projCombo.getSelectedItem())){
                    project = p;
                }
            }
            if(e.getSource() == viewSampleButton){
                for(DownloadBatchPopupListener listener:listeners){
                    listener.getSampleImage(project);
                }
            }else if (e.getSource() == cancelButton){
                close();
            } else if (e.getSource() == downloadButton){
                for(DownloadBatchPopupListener listener:listeners){
                    listener.downloadBatch(project.getProjectid());
                }
                close();
            }
        }
    };

    private void createComponents() {
        this.setLocationRelativeTo(null);
        JLabel projLabel = new JLabel("Project: ");
        projCombo = new JComboBox<String>(titles);
        viewSampleButton = new JButton("View Sample");
        viewSampleButton.addActionListener(buttonListener);

        JPanel projectPanel = new JPanel();
        projectPanel.add(projLabel);
        projectPanel.add(projCombo);
        projectPanel.add(viewSampleButton);

        downloadButton = new JButton("Download");
        cancelButton = new JButton("Cancel");
        downloadButton.addActionListener(buttonListener);
        cancelButton.addActionListener(buttonListener);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(downloadButton);

        this.add(projectPanel, BorderLayout.NORTH);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.pack();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        titles = new String[projects.size()];
        for(int i = 0; i<projects.size();i++){
            titles[i] = projects.get(i).getTitle();
        }
    }

    public interface DownloadBatchPopupListener {
        public void getSampleImage(Project project);
        public void downloadBatch(int projectID);
    }
}
