package shared.Results;

import server.Models.Batch;

/**
 * Created by Matt on 3/10/2015.
 */
public class DownloadBatch_Result extends Result {
    private Batch batch;

    @Override
    public String toString() {
        if(isError()){
            return super.toString();
        } else {
            return batch.toString();
        }
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }
}
