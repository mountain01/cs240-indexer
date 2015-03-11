package shared.Results;

/**
 * Created by Matt on 3/10/2015.
 */
public class Result {
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    boolean error = false;

    @Override
    public String toString() {
        return error ?"FAILED\n":"";
    }
}
