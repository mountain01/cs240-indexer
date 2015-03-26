package search.views;

import server.Models.Project;
import server.Models.SearchResult;
import shared.Communicator.ClientCommunicator;
import shared.Params.GetFields_Params;
import shared.Params.GetProjects_Params;
import shared.Params.Search_Params;
import shared.Params.ValidateUser_Params;
import shared.Results.GetFields_Result;
import shared.Results.GetProjects_Result;
import shared.Results.Search_Result;
import shared.Results.ValidateUser_Result;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
    private ImagesPanel imagePanel;

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
                    imagePanel.imageList.removeAllItems();
                    doSearch(username,password);
                } else {
                    imagePanel.setVisible(false);
                }
            }
        });
        addImages();
        pack();
        setMinimumSize(getSize());
    }

    private void addImages() {
        imagePanel = new ImagesPanel();
        add(imagePanel);
        imagePanel.setVisible(false);
        imagePanel.viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputStream input;
                try{
                    String url = (String) imagePanel.imageList.getSelectedItem();
                    input = new URL(url).openStream();
                    BufferedImage pic = ImageIO.read(input);
                    //make frame for image
                    JFrame f = new JFrame();
                    f.setTitle(url);
                    ImageIcon ico = new ImageIcon(pic);
                    JPanel j = new JPanel();
                    j.add(new JLabel(ico));
                    j.revalidate();
                    f.add(new JScrollPane(j));
                    f.pack();
                    f.setVisible(true);
                    pack();

                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        pack();
    }

    private void doSearch(String username,String password){
        ArrayList<String> tempSearchValues = new ArrayList<String>();
        ArrayList<String> tempFieldIds = new ArrayList<String>();
        //convert list of search values into array
        for(String s:searchPanel.getValuesField().split(",")){
            s = s.trim().toUpperCase();
            if(!tempSearchValues.contains(s)){
                tempSearchValues.add(s);
            }
        }
        String[] searchValues = tempSearchValues.toArray(new String[tempSearchValues.size()]);
        //Get field id's for search
        int fieldId = 0;
        for(Component c:projPanel.getComponents()){
            JCheckBox field = new JCheckBox();
            if(field.getClass() == c.getClass()){
                field = (JCheckBox) c;
                ++fieldId;
                if(field.isSelected()){
                    tempFieldIds.add(Integer.toString(fieldId));
                }
            }
        }
        String[] fieldIds = tempFieldIds.toArray(new String[tempFieldIds.size()]);
        Search_Params sp = new Search_Params(username,password,searchValues,fieldIds);
        Search_Result sr = clientComm.search(sp);
        updateImages(sr.getResults());
    }

    private void updateImages(ArrayList<SearchResult> results) {
        if(results.size() >0){
            ArrayList<String> unique = new ArrayList<String>();
            for(SearchResult s:results){
                String word = s.getUrl();
                if(!unique.contains(word)) {
                    imagePanel.imageList.addItem(word);
                    unique.add(word);
                }
            }
            imagePanel.setSize(imagePanel.getPreferredSize());
            pack();
            imagePanel.setVisible(true);
        }else{
            imagePanel.imageList.removeAllItems();
            imagePanel.setVisible(false);
        }
    }
}
