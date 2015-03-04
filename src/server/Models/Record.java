package server.Models;

/**
 * Created by Matt on 10/14/2014.
 */
public class Record {
    int recordid;
    int batchid;

    /**
     * g
     * @return associated record id
     */
    public int getRecordid() {
        return recordid;
    }

    /**
     * set batches associated record id
     * @param recordid
     */
    public void setRecordid(int recordid) {
        this.recordid = recordid;
    }

    /**
     *
     * @return batch id
     */
    public int getBatchid() {
        return batchid;
    }

    /**
     * set batch id number for batch
     * @param batchid
     */
    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }
}
