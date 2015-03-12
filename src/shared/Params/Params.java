package shared.Params;

/**
 * Created by Matt on 3/10/2015.
 */
public class Params {
    private String username;
    private String password;

    public Params(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Params() {

    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
