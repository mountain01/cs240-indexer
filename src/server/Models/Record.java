package server.Models;

import java.util.Map;

/**
 * Created by Matt on 10/14/2014.
 */
public class Record {
    int recordid;
    int batchid;
    int colid;

    public int getColid() {
        return colid;
    }

    public void setColid(int colid) {
        this.colid = colid;
    }

    public Map<Field, String> getValues() {
        return values;
    }

    public void setValues(Map<Field, String> values) {
        this.values = values;
    }

    Map<Field,String> values;

    /**
     *
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
