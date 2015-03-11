package server.Models;

import java.util.List;

/**
 * Created by Matt on 10/14/2014.
 */
public class Batch {
    int batchid;
    int projectid;
    int firstycoord;
    int recordHeight;
    int numRecords;
    List<Field> fields;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    List<Record> records;

    public int getFirstycoord() {
        return firstycoord;
    }

    public void setFirstycoord(int firstycoord) {
        this.firstycoord = firstycoord;
    }

    public int getRecordHeight() {
        return recordHeight;
    }

    public void setRecordHeight(int recordHeight) {
        this.recordHeight = recordHeight;
    }

    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    String imagefilepath;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(batchid+"\n"+projectid+"\n"+imagefilepath+"\n"+firstycoord
                +"\n"+recordHeight+"\n"+numRecords+"\n"+fields.size()+"\n");
        for(Field f:fields){
            string.append(f.toString());
        }
        return string.toString();
    }

    boolean complete;

    /**
     *
     * @return the batch id
     */
    public int getBatchid() {
        return batchid;
    }

    /**
     * set the batch's id
     * @param batchid
     */
    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }

    /**
     *
     * @return associated project id
     */
    public int getProjectid() {
        return projectid;
    }

    /**
     * set the batch's associated project id
     * @param projectid
     */
    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    /**
     *
     * @return image's file path
     */
    public String getImagefilepath() {
        return imagefilepath;
    }

    /**
     * set the file path for the batch's image
     * @param imagefilepath
     */
    public void setImagefilepath(String imagefilepath) {
        this.imagefilepath = imagefilepath;
    }

    /**
     *
     * @return true if completed else false
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * set batch's completed flag
     * @param complete
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
