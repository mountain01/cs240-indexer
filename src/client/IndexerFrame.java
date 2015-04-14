package client;

import client.menus.IndexMenu;
import client.menus.IndexerButtonBar;
import client.models.IndexerDataModel;
import client.models.QualityCheckModel;
import client.popups.DownloadBatchPopup;
import client.popups.ViewSamplePopup;
import client.popups.userLogInWindow;
import server.Models.Batch;
import server.Models.Project;
import server.Models.User;
import shared.Communicator.ClientCommunicator;
import shared.Params.DownloadBatch_Params;
import shared.Params.GetProjects_Params;
import shared.Params.GetSampleImage_Params;
import shared.Params.SubmitBatch_Params;
import shared.Results.GetProjects_Result;
import shared.Results.SubmitBatch_Result;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 4/9/2015.
 */
public class IndexerFrame extends JFrame implements userLogInWindow.LoginListener, IndexMenu.IndexMenuListener, DownloadBatchPopup.DownloadBatchPopupListener, IndexerButtonBar.ButtonBarListener, QualityCheckModel.SeeSuggestionsListener{

    // menu
    private IndexMenu menuBar;
    // button bar
    private IndexerButtonBar buttons;
    // picture spot
    private ImageViewer imageViewer;
    // bottom section
    private IndexerFooter footer;
    private IndexerDataModel model;
    private QualityCheckModel qCheck;
    private User user;
    private boolean hasBatch;

     public IndexerFrame() {
         super("Indexer");
         setSize(1000, 700);
         setLocation(500, 500);
         setResizable(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         hasBatch = false;
     }

    private void createComponents(){

         menuBar = new IndexMenu();
         setJMenuBar(menuBar);
        menuBar.addListener(this);

         buttons = new IndexerButtonBar();
         add(buttons, BorderLayout.NORTH);
        buttons.addListener(this);

        model = new IndexerDataModel();
        qCheck = new QualityCheckModel();
        qCheck.addListener(this);

         imageViewer = new ImageViewer(model);

         footer = new IndexerFooter(model,qCheck);
         JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,imageViewer,footer);
         splitPane.setDividerLocation(400);
         this.add(splitPane, BorderLayout.CENTER);
     }

    public void start(){displayLogin();}

    private void displayLogin() {
        userLogInWindow login = new userLogInWindow();
        login.addListener(this);
        login.setVisible(true);
    }

    @Override
    public void login(User user) {
        this.createComponents();
        this.setVisible(true);
        this.user = user;

        // if no current batch
        hasBatch = true;
    }

    @Override
    public void exit() {
        //save state of program
        System.exit(0);
    }

    @Override
    public void logOut() {
        //save state of program
        this.setVisible(false);
        displayLogin();
    }

    @Override
    public void requestBatch() {
        GetProjects_Params gpp = new GetProjects_Params(user.getUsername(),user.getPassword());
        GetProjects_Result gpr = ClientCommunicator.getInstance().getProjects(gpp);
        DownloadBatchPopup window = new DownloadBatchPopup(this,gpr.getProjects());
        window.addListener(this);
        window.setVisible(true);
    }

    @Override
    public void getSampleImage(Project project) {
        GetSampleImage_Params params = new GetSampleImage_Params();
        params.setUsername(user.getUsername());
        params.setPassword(user.getPassword());
        params.setProjectId(project.getProjectid());
        String url = ClientCommunicator.getInstance().getSampleImage(params).getUrl();
        ViewSamplePopup window = new ViewSamplePopup(this,"Sample Image from " + project.getTitle(), url);
        window.setVisible(true);
    }

    @Override
    public void downloadBatch(int projectID) {
        DownloadBatch_Params params = new DownloadBatch_Params();
        params.setUsername(user.getUsername());
        params.setPassword(user.getPassword());
        params.setProjectid(projectID);
        Batch batch = ClientCommunicator.getInstance().downloadBatch(params).getBatch();
        buttons.setEnabled(true);
        footer.setBatch(batch);
        imageViewer.setBatch(batch);
        menuBar.setHasBatch(true);
        this.hasBatch = true;
    }

    @Override
    public void zoomIn() {
        imageViewer.zoomIn();
    }

    @Override
    public void zoomOut() {
        imageViewer.zoomOut();
    }

    @Override
    public void invertImage() {
        imageViewer.invert();
    }

    @Override
    public void toggleHighlight() {
        imageViewer.highlight();
    }

    @Override
    public void save() {

    }

    @Override
    public void submit() {
        SubmitBatch_Params params = new SubmitBatch_Params();
        params.setUsername(user.getUsername());
        params.setPassword(user.getPassword());
        ArrayList<String[]> values = new ArrayList<String[]>();
        for(Object[] record:footer.getRecordValues()){
            values.add((String[]) record);
        }
        params.setFieldValues(values);
        params.setBatchId(imageViewer.getBatch().getBatchid());
        SubmitBatch_Result result = ClientCommunicator.getInstance().submitBatch(params);
        if(result.isError()){
            JOptionPane.showMessageDialog(this,
                    "There was an error submitting your batch",
                    "Submit Batch Failed",JOptionPane.ERROR_MESSAGE);
        } else {
            hasBatch = false;
            imageViewer.removeBatch();
            footer.removeBatch();
            buttons.setEnabled(false);
            menuBar.setHasBatch(hasBatch);
        }
    }

    @Override
    public void seeSuggestions(int row, int col, List<String> similar) {

    }
}
