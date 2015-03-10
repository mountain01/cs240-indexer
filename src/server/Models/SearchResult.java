package server.Models;

/**
 * Created by Matt on 3/10/2015.
 */
public class SearchResult {
    private int batchid;
    private String url;
    private int numRecords;
    private int fieldid;

    @Override
    public String toString() {
        return batchid+"\n"+url+"\n"+numRecords+"\n"+fieldid+"\n";
    }

    public int getBatchid() {
        return batchid;
    }

    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }

    public int getFieldid() {
        return fieldid;
    }

    public void setFieldid(int fieldid) {
        this.fieldid = fieldid;
    }
}
