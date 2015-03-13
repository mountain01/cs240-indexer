package shared.Params;

/**
 * Created by Matt on 3/10/2015.
 */
public class GetSampleImage_Params extends Params {
    private int projectId;

    public GetSampleImage_Params(String username, String password, int projectId) {
        super(username, password);
        this.projectId = projectId;
    }

    public GetSampleImage_Params() {

    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
