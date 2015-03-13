package shared.Params;

/**
 * Created by Matt on 3/10/2015.
 */
public class DownloadBatch_Params extends Params{
    public DownloadBatch_Params() {

    }

    public int getProjectid() {
        return projectid;
    }

    public DownloadBatch_Params(String username, String password, int projectid) {
        super(username, password);
        this.projectid = projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    private int projectid;
}
