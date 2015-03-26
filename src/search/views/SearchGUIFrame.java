package search.views;

import server.Models.Project;
import shared.Communicator.ClientCommunicator;
import shared.Params.GetFields_Params;
import shared.Params.GetProjects_Params;
import shared.Params.ValidateUser_Params;
import shared.Results.GetFields_Result;
import shared.Results.GetProjects_Result;
import shared.Results.ValidateUser_Result;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Matt on 3/25/2015.
 */
public class SearchGUIFrame extends JFrame {
    private CredentialsPanel credentialPanel;
    private final String host = "localhost";
    private final String port = "8080";
    private ClientCommunicator clientComm;
    private SearchGUIFrame frame = this;
    private ProjectFieldsPanel projPanel;
    private SearchPanel searchPanel;

    public SearchGUIFrame(){
        super();
        // title of window
        setTitle("Search GUI");
        // how to close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set layout
        setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        // set size of window
        setSize(1000,100);
        setMinimumSize(this.getSize());
        // set position of window
        setLocation(500,500);

        addCredentialPanel();
    }

    private void addCredentialPanel() {
        add(Box.createRigidArea(new Dimension(0,10)));

        credentialPanel = new CredentialsPanel();
        credentialPanel.setHostField(host);
        credentialPanel.setPortField(port);
        add(credentialPanel);

        credentialPanel.executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientComm = new ClientCommunicator();
                clientComm.setHost(credentialPanel.getHostField());
                clientComm.setPort(Integer.parseInt(credentialPanel.getPortField()));
                ValidateUser_Params vup = new ValidateUser_Params(credentialPanel.getUsernameField(), credentialPanel.getPasswordField());
                ValidateUser_Result vur = clientComm.validateUser(vup);
                if (vur.isValidUser()) {
                    credentialPanel.executeButton.setVisible(false);
                    credentialPanel.portField.setEnabled(false);
                    credentialPanel.hostField.setEnabled(false);
                    credentialPanel.usernameField.setEnabled(false);
                    credentialPanel.passwordField.setEnabled(false);
                    credentialPanel.setVisible(false);
                    addSearchPanel(vup.getUsername(), vup.getPassword());
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                }
            }
        });
        add(Box.createRigidArea(new Dimension(0,10)));
    }

    private void addSearchPanel(final String username, final String password) {
        ArrayList<ProjectFields> fields = new ArrayList<ProjectFields>();
        GetProjects_Params gpp = new GetProjects_Params(username,password);
        GetProjects_Result gpr = clientComm.getProjects(gpp);
        for(Project p:gpr.getProjects()){
            GetFields_Params gfp = new GetFields_Params();
            gfp.setProjectid(p.getProjectid());
            gfp.setPassword(password);
            gfp.setUsername(username);
            GetFields_Result gfr = clientComm.getFields(gfp);
            fields.add(new ProjectFields(gfr.getFields(),p));
        }
        add(Box.createRigidArea(new Dimension(0,10)));

        projPanel = new ProjectFieldsPanel(fields);
        add(projPanel);
        add(Box.createRigidArea(new Dimension(0,10)));
        searchPanel = new SearchPanel();
        add(searchPanel);
        add(Box.createRigidArea(new Dimension(0,10)));
        searchPanel.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchPanel.getValuesField().length() > 0){
                    doSearch(username,password);
                }
            }
        });

        pack();
        setMinimumSize(getSize());
    }

    private void doSearch(String username,String password){

    }
}
