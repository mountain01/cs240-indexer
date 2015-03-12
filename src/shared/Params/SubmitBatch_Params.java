package shared.Params;

import java.util.ArrayList;

/**
 * Created by Matt on 3/10/2015.
 */
public class SubmitBatch_Params extends Params {
    private ArrayList<String[]> fieldValues;
    private int batchId;

    public SubmitBatch_Params() {
        super();
    }

    public ArrayList<String[]> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(ArrayList<String[]> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public SubmitBatch_Params(String username, String password, ArrayList<String[]> fieldValues, int batchId) {
        super(username, password);
        this.fieldValues = fieldValues;
        this.batchId = batchId;
    }
}
