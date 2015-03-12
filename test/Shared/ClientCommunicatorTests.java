package Shared;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Importer.Importer;
import server.Server;
import shared.Communicator.ClientCommunicator;
import shared.Params.*;
import shared.Results.*;
import sun.misc.Cleaner;

import java.util.ArrayList;

/**
 * Created by Matt on 3/12/2015.
 */
public class ClientCommunicatorTests {

    @BeforeClass
    public static void setupAllTestStuff(){
        Server.startTest();
    }
    @Before
    public void init(){
        new Importer().dataImport("Records/Records.xml");
        ClientCommunicator.getInstance().setPort(34590);
    }

    @AfterClass
    public static void endTests(){
        Server.endTest();
    }

    @Test
    public void downloadFileTest(){
        byte[] result = ClientCommunicator.getInstance().downloadFile("/test.txt");
        Assert.assertTrue(new String(result).equals("This was a test"));
    }

    @Test
    public void getValidUserTest(){
        ValidateUser_Params params = new ValidateUser_Params("test1","test1");
        ValidateUser_Result result = ClientCommunicator.getInstance().validateUser(params);
        Assert.assertFalse(result.isError());
        Assert.assertTrue(result.isValidUser());
        Assert.assertTrue("test1".equals(result.getUser().getPassword()));
        Assert.assertTrue("test1".equals(result.getUser().getUsername()));
        Assert.assertTrue("Test".equals(result.getUser().getFirstname()));
        Assert.assertTrue("One".equals(result.getUser().getLastname()));
        Assert.assertTrue("test1@gmail.com".equals(result.getUser().getEmail()));
        Assert.assertSame(0, result.getUser().getRecordcount());
    }

    @Test
    public void getInvalidUserTest(){
        ValidateUser_Params params = new ValidateUser_Params("test1","test");
        ValidateUser_Result result = ClientCommunicator.getInstance().validateUser(params);
        Assert.assertFalse(result.isError());
        Assert.assertFalse(result.isValidUser());
        Assert.assertNull(result.getUser());
    }

    @Test
    public void getProjectsTest(){
        GetProjects_Params params = new GetProjects_Params("test1","test1");
        GetProjects_Result result = ClientCommunicator.getInstance().getProjects(params);
        Assert.assertFalse(result.isError());
        Assert.assertSame(3, result.getProjects().size());
    }

    @Test
    public void getSampleImageTest(){
        GetSampleImage_Params params = new GetSampleImage_Params("test1","test1",1);
        GetSampleImage_Result result = ClientCommunicator.getInstance().getSampleImage(params);
        GetSampleImage_Result result2 = ClientCommunicator.getInstance().getSampleImage(params);
        Assert.assertFalse(result.isError());
        Assert.assertFalse(result2.isError());
    }

    @Test
    public void getFieldsTest(){
        GetFields_Params params = new GetFields_Params("test1","test1",1);
        GetFields_Result result = ClientCommunicator.getInstance().getFields(params);
        Assert.assertFalse(result.isError());
        Assert.assertSame(4, result.getFields().size());
    }

    @Test
    public void downloadBatchTest(){
        DownloadBatch_Params params = new DownloadBatch_Params("test1","test1",1);
        DownloadBatch_Result result = ClientCommunicator.getInstance().downloadBatch(params);
        Assert.assertFalse(result.isError());
        Assert.assertNotNull(result.getBatch());
    }

    @Test
    public void submitBatchTest(){
        DownloadBatch_Params params1 = new DownloadBatch_Params("test1","test1",1);
        DownloadBatch_Result result1 = ClientCommunicator.getInstance().downloadBatch(params1);
        SubmitBatch_Params params2 = new SubmitBatch_Params();
        params2.setUsername("test1");
        params2.setPassword("test1");
        params2.setBatchId(result1.getBatch().getBatchid());
        String[] recordValues = {"a","a","a","a"};
        String[] recordValues2 = {"R","R","R","R"};
        ArrayList<String[]> list = new ArrayList<String[]>();
        list.add(recordValues);
        list.add(recordValues2);
        params2.setFieldValues(list);
        SubmitBatch_Result result2 = ClientCommunicator.getInstance().submitBatch(params2);
        Assert.assertFalse(result1.isError());
        Assert.assertFalse(result2.isError());
    }

    @Test
    public void submitUnassignedBatchTest(){
        DownloadBatch_Params params1 = new DownloadBatch_Params("test1","test1",1);
        DownloadBatch_Result result1 = ClientCommunicator.getInstance().downloadBatch(params1);
        SubmitBatch_Params params2 = new SubmitBatch_Params();
        params2.setUsername("test1");
        params2.setPassword("test1");
        params2.setBatchId(1);
        String[] recordValues = {"a","a","a","a"};
        String[] recordValues2 = {"R","R","R","R"};
        ArrayList<String[]> list = new ArrayList<String[]>();
        list.add(recordValues);
        list.add(recordValues2);
        params2.setFieldValues(list);
        SubmitBatch_Result result2 = ClientCommunicator.getInstance().submitBatch(params2);
        Assert.assertFalse(result1.isError());
        Assert.assertTrue(result2.isError());
    }

    @Test
    public void searchLowerCaseTest(){
        String[] fieldids={"13","10"};
        String[] values = {"alaska native","fox","kevin"};
        Search_Params params = new Search_Params("test1","test1",values,fieldids);
        Search_Result result = ClientCommunicator.getInstance().search(params);
        Assert.assertFalse(result.isError());
        Assert.assertSame(24,result.getResults().size());
    }

    @Test
    public void searchUpperCaseTest(){
        String[] fieldids={"13","10"};
        String[] values = {"ALASKA NATIVE","FOX","KEVIN"};
        Search_Params params = new Search_Params("test1","test1",values,fieldids);
        Search_Result result = ClientCommunicator.getInstance().search(params);
        Assert.assertFalse(result.isError());
        Assert.assertSame(24,result.getResults().size());
    }

    @Test
    public void searchMixedCaseTest(){
        String[] fieldids={"13","10"};
        String[] values = {"alAska natIve","FoX","Kevin"};
        Search_Params params = new Search_Params("test1","test1",values,fieldids);
        Search_Result result = ClientCommunicator.getInstance().search(params);
        Assert.assertFalse(result.isError());
        Assert.assertSame(24,result.getResults().size());
    }

    @Test
    public void searchNotWordTest(){
        String[] fieldids={"13","10"};
        String[] values = {"jfeiajeks"};
        Search_Params params = new Search_Params("test1","test1",values,fieldids);
        Search_Result result = ClientCommunicator.getInstance().search(params);
        Assert.assertFalse(result.isError());
        Assert.assertSame(0,result.getResults().size());
    }
}
