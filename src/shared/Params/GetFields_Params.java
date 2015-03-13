package shared.Params;

/**
 * Created by Matt on 3/10/2015.
 */
public class GetFields_Params extends Params {
    private int projectid = -1;

    public GetFields_Params(String username, String password, int projectid) {
        super(username, password);
        this.projectid = projectid;
    }

    public GetFields_Params() {

    }

    public int getProjectid() {
        return projectid;

    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}
