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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batch)) return false;

        Batch batch = (Batch) o;

        if (batchid != batch.batchid) return false;
        if (complete != batch.complete) return false;
        if (firstycoord != batch.firstycoord) return false;
        if (numRecords != batch.numRecords) return false;
        if (projectid != batch.projectid) return false;
        if (recordHeight != batch.recordHeight) return false;
        if (fields != null ? !fields.equals(batch.fields) : batch.fields != null) return false;
        if (imagefilepath != null ? !imagefilepath.equals(batch.imagefilepath) : batch.imagefilepath != null)
            return false;
        if (records != null ? !records.equals(batch.records) : batch.records != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = batchid;
        result = 31 * result + projectid;
        result = 31 * result + firstycoord;
        result = 31 * result + recordHeight;
        result = 31 * result + numRecords;
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        result = 31 * result + (records != null ? records.hashCode() : 0);
        result = 31 * result + (imagefilepath != null ? imagefilepath.hashCode() : 0);
        result = 31 * result + (complete ? 1 : 0);
        return result;
    }
}
