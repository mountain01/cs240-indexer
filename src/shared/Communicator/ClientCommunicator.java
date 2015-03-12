package shared.Communicator;

import server.Models.Batch;
import server.Models.Field;
import server.Models.SearchResult;
import shared.Params.*;
import shared.Results.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by Matt on 3/10/2015.
 */
public class ClientCommunicator {

    private String SERVER_HOST = "localhost";

    public void setPort(int SERVER_PORT) {
        this.SERVER_PORT = SERVER_PORT;
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }

    public void setHost(String newHost) {
        this.SERVER_HOST = newHost;
        URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    }

    private  int SERVER_PORT = 8080;
    private String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    private XStream xmlStream = new XStream(new DomDriver());
    private static ClientCommunicator instance;

    public static ClientCommunicator getInstance(){
        if(instance == null){
            instance = new ClientCommunicator();
        }
        return instance;
    }



    public DownloadBatch_Result downloadBatch(DownloadBatch_Params params){
        DownloadBatch_Result result = (DownloadBatch_Result)doGet("/DownloadBatch/"+params.getProjectid(),params);
        if(result == null){
            result = new DownloadBatch_Result();
            result.setError(true);
        }
        Batch batch = result.getBatch();
        if(batch != null){
            for(Field f:batch.getFields()){
                if(f.getHelphtml() != null && !f.getHelphtml().equals("")){
                    f.setHelphtml(URL_PREFIX+"/"+f.getHelphtml());
                }
                if(f.getKnowndatahtml() != null && !f.getKnowndatahtml().equals("")){
                    f.setKnowndatahtml(URL_PREFIX + "/" + f.getKnowndatahtml());
                }
            }
        }
        batch.setImagefilepath(URL_PREFIX+"/"+batch.getImagefilepath());
        return result;
    }

    public GetFields_Result getFields(GetFields_Params params){
        GetFields_Result result = (GetFields_Result)doGet("/GetFields/"+params.getProjectid(),params);
        if (result == null){
            result = new GetFields_Result();
            result.setError(true);
        }
        if(result.getFields() != null){
            for(Field f:result.getFields()){
                f.setKnowndatahtml(URL_PREFIX+"/"+f.getKnowndatahtml());
                f.setHelphtml(URL_PREFIX+"/"+f.getHelphtml());
            }
        }
        return result;
    }

    public GetProjects_Result getProjects(GetProjects_Params params){
        GetProjects_Result result = (GetProjects_Result)doGet("/GetProject",params);
        if(result == null){
            result = new GetProjects_Result();
            result.setError(true);
        }
        return result;
    }

    public GetSampleImage_Result getSampleImage(GetSampleImage_Params params){
        GetSampleImage_Result result = (GetSampleImage_Result)doGet("/GetSampleImage/"+params.getProjectId(),params);
        if(result == null){
            result = new GetSampleImage_Result();
            result.setError(true);
            return result;
        }
        result.setUrl(URL_PREFIX + "/" + result.getUrl());
        return result;
    }

    public Search_Result search(Search_Params params){
        Search_Result result = (Search_Result) doPost("/Search",params);
        if(result == null){
            result = new Search_Result();
            result.setError(true);
        }
        if(result.getResults() != null){
            for(SearchResult rs:result.getResults()){
                rs.setUrl(URL_PREFIX+"/"+rs.getUrl());
            }
        }
        return result;
    }

    public SubmitBatch_Result submitBatch(SubmitBatch_Params params){
        SubmitBatch_Result result = (SubmitBatch_Result) doPost("/SubmitBatch/"+params.getBatchId(),params);
        if(result == null){
            result = new SubmitBatch_Result();
            result.setError(true);
        }
        return result;
    }

    public ValidateUser_Result validateUser(ValidateUser_Params params){
        ValidateUser_Result result = (ValidateUser_Result) doGet("/ValidateUser",params);
        if(result == null){
            result = new ValidateUser_Result();
            result.setError(true);
        }
        return result;
    }

    public byte[] downloadFile(String filename){
        byte[] result = null;
        try {
            URL url = new URL(URL_PREFIX + filename);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.connect();
            result = toByteArray(connection.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] result = new byte[1024];
        int read;
        while ((read = inputStream.read(result, 0, result.length)) != -1) {
            byteStream.write(result, 0, read);
        }
        byteStream.flush();
        return  byteStream.toByteArray();
    }

    private Object doGet(String urlPath, Params params)  {
        Object result = null;
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.addRequestProperty("authorization", params.getUsername() + ":" + params.getPassword());
            connection.connect();
            result = xmlStream.fromXML(connection.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object doPost(String urlPath, Params params) {
        Object result = null;
        try {
            URL url = new URL(URL_PREFIX + urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);
            connection.addRequestProperty("authorization", params.getUsername() + ":" + params.getPassword());
            connection.connect();
            xmlStream.toXML(params, connection.getOutputStream());
            connection.getOutputStream().close();
            result = xmlStream.fromXML(connection.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

