package server;

import server.DAOs.Database;
import server.Models.*;
import shared.Params.Search_Params;
import shared.Params.SubmitBatch_Params;
import shared.Results.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Matt on 11/1/2014.
 */
public class ServerFacade {
    
    public GetProjects_Result getProjects(String auth){
        GetProjects_Result result = new GetProjects_Result();
        if(validateUser(auth).isValidUser()){
            Database db = new Database();
            db.startTransaction();
            ArrayList<Project> projects = db.getProjectDAO().getProjects();
            db.endTransaction();
            if(db.wasSuccesful()){
                result.setProjects(projects);
            }else{
                result.setError(true);
            }
        } else {
            result.setError(true);
        }
        return result;
    }

    public Search_Result search(String auth,Search_Params params){
        Search_Result result = new Search_Result();
        if(!validSearchParams(params)){
            result.setError(true);
        }else{
            Database db = new Database();
            ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
            if(validateUser(auth).isValidUser()){
                db.startTransaction();
                for(String fieldId:params.getFieldIds()){
                    searchResults.addAll(db.getValueDAO().searchValues(Integer.parseInt(fieldId),params.getSearchValues()));
                }
                for(SearchResult sr:searchResults){
                    sr.setUrl(db.getBatchDAO().getBatchById(sr.getBatchid()).getImagefilepath());
                }
                db.endTransaction();
                if(db.wasSuccesful()){
                    result.setResults(searchResults);
                } else {
                    result.setError(true);
                }
            }else{
                result.setError(true);
            }
        }
        return result;
    }

    private boolean validSearchParams(Search_Params params){
        return !(params.getFieldIds().length == 0 && params.getSearchValues().length == 0);
    }

    public ValidateUser_Result validateUser(String auth){
        ValidateUser_Result result = new ValidateUser_Result();
        if(!auth.matches(".+:.+")){
            result.setError(true);
        } else {
            Database database = new Database();
            String[] userInfo = auth.split(":");
            database.startTransaction();
            User user = database.getUserDAO().checkUser(userInfo[0],userInfo[1]);
            database.endTransaction();
            result.setUser(user);
            result.setError(!database.wasSuccesful());
            result.setValidUser(user != null);
        }
        return result;
    }

    public GetSampleImage_Result getSampleImage(String auth,int projId){
        GetSampleImage_Result result = new GetSampleImage_Result();
        if(validateUser(auth).isValidUser()) {
            if (projId == 0) {
                result.setError(true);
            } else {
                Database db = new Database();
                db.startTransaction();
                ArrayList<Batch> batches = db.getBatchDAO().getBatchesByProjectID(projId);
                db.endTransaction();
                if (db.wasSuccesful()) {
                    result.setUrl(batches.get(new Random().nextInt(batches.size())).getImagefilepath());
                }else{
                    result.setError(true);
                }
            }
        } else{
            result.setError(true);
        }
        return result;
    }

    public byte[] downloadFile(String url){
        File file = new File("Server/data/" + url);
        byte[] result = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(result);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                assert fileInputStream != null;
                fileInputStream.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public DownloadBatch_Result downloadBatch(String auth,int projid){
        DownloadBatch_Result result = new DownloadBatch_Result();
        Database db = new Database();
        ValidateUser_Result validUser = validateUser(auth);
        if(projid ==0){
            result.setError(true);
        } else{
            if(validUser.isValidUser() && validUser.getUser().getCurrbatch() == 0){
                db.startTransaction();
                ArrayList<Batch> batches = db.getBatchDAO().getBatchesByProjectID(projid);
                if(batches == null ||batches.size() == 0){
                    result.setError(true);
                    db.endTransaction();
                }else{
                    Batch userBatch = batches.get(new Random().nextInt(batches.size()));
                    userBatch.setFields(db.getFieldDAO().getFieldsbyProjectId(projid));
                    userBatch.setRecords(db.getRecordDAO().getRecordsByBatchId(userBatch.getBatchid()));
                    Project project = db.getProjectDAO().getProjectById(projid);
                    if(project == null){
                        result.setError(true);
                        db.endTransaction();
                    }else{
                        userBatch.setFirstycoord(project.getFirstycoord());
                        userBatch.setRecordHeight(project.getRecordheight());
                        userBatch.setNumRecords(project.getRecordsperimage());
                        validUser.getUser().setCurrbatch(userBatch.getBatchid());
                        db.getUserDAO().updateUser(validUser.getUser());
                        db.endTransaction();
                        if(db.wasSuccesful()){
                            result.setBatch(userBatch);
                        }else{
                            result.setError(true);
                        }
                    }
                }
            }else {
                result.setError(true);
            }
        }
        return result;
    }

    public DownloadBatch_Result retrieveBatch(String auth,int batchid){
        DownloadBatch_Result result = new DownloadBatch_Result();
        Database db = new Database();
        ValidateUser_Result validUser = validateUser(auth);
        if(batchid ==0){
            result.setError(true);
        } else{
            if(validUser.isValidUser() && validUser.getUser().getCurrbatch() == batchid){
                db.startTransaction();
                Batch userBatch = db.getBatchDAO().getBatchById(batchid);
                if(userBatch == null){
                    result.setError(true);
                    db.endTransaction();
                }else{
                    userBatch.setFields(db.getFieldDAO().getFieldsbyProjectId(userBatch.getProjectid()));
                    userBatch.setRecords(db.getRecordDAO().getRecordsByBatchId(userBatch.getBatchid()));
                    Project project = db.getProjectDAO().getProjectById(userBatch.getProjectid());
                    if(project == null){
                        result.setError(true);
                        db.endTransaction();
                    }else{
                        userBatch.setFirstycoord(project.getFirstycoord());
                        userBatch.setRecordHeight(project.getRecordheight());
                        userBatch.setNumRecords(project.getRecordsperimage());
                        db.endTransaction();
                        if(db.wasSuccesful()){
                            result.setBatch(userBatch);
                        }else{
                            result.setError(true);
                        }
                    }
                }
            }else {
                result.setError(true);
            }
        }
        return result;
    }

    public SubmitBatch_Result submitBatch(String auth, SubmitBatch_Params params){
        SubmitBatch_Result result = new SubmitBatch_Result();
        if(validBatchParams(params)){
            Database db = new Database();
            db.startTransaction();
            Batch batch = db.getBatchDAO().getBatchById(params.getBatchId());
            Project project = db.getProjectDAO().getProjectById(batch.getProjectid());
            ValidateUser_Result validUser = validateUser(auth);
            User user = validUser.getUser();
            if(validUser.isValidUser()&&user.getCurrbatch() == params.getBatchId()&&!batch.isComplete()){
                ArrayList<Field> fields = db.getFieldDAO().getFieldsbyProjectId(batch.getProjectid());
                int colNum = 1;
                for(String[] values:params.getFieldValues()){
                    Record r = new Record();
                    r.setBatchid(params.getBatchId());
                    r.setValues(new HashMap<Field, String>());
                    r.setColid(colNum++);
                    for(int i = 0;i<values.length;i++){
                        String val = values[i];
                        if(val==null){
                            r.getValues().put(fields.get(i),"");
                        }else{
                            r.getValues().put(fields.get(i),val);
                        }
                    }
                    db.getRecordDAO().addRecord(r);
                }
                user.setCurrbatch(0);
                user.setRecordcount(project.getRecordsperimage()+user.getRecordcount());
                db.getUserDAO().updateUserBatchComplete(user);
                db.getBatchDAO().setBatchComplete(params.getBatchId());
                db.endTransaction();
                if(!db.wasSuccesful()){
                    result.setError(true);
                }
            }else{
                result.setError(true);
            }
        }else{
            result.setError(true);
        }
        return result;
    }

    private boolean validBatchParams(SubmitBatch_Params params){
        return params.getBatchId() != 0 && params.getFieldValues().size() != 0;
    }

    public GetFields_Result getFields(String auth,int projid){
        GetFields_Result result = new GetFields_Result();
        if(projid == 0){
            result.setError(true);
        } else {
            if(validateUser(auth).isValidUser()){
                Database db = new Database();
                db.startTransaction();
                ArrayList<Field> fields = projid == -1? db.getFieldDAO().getFields(): db.getFieldDAO().getFieldsbyProjectId(projid);
                db.endTransaction();
                if(fields.size() == 0){
                    result.setError(true);
                }
                if(db.wasSuccesful()){
                    result.setFields(fields);
                }else{
                    result.setError(true);
                }
            }else{
                result.setError(true);
            }
        }
        return result;
    }
}
