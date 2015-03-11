package server.Models;

/**
 * Created by Matt on 3/10/2015.
 */
public class SearchResult {
    private int batchid;
    private String url;
    private int recordNumber;
    private int fieldid;

    @Override
    public String toString() {
        return batchid+"\n"+url+"\n"+recordNumber+"\n"+fieldid+"\n";
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

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int numRecords) {
        this.recordNumber = numRecords;
    }

    public int getFieldid() {
        return fieldid;
    }

    public void setFieldid(int fieldid) {
        this.fieldid = fieldid;
    }
}
