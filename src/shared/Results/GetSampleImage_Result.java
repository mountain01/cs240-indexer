package shared.Results;

/**
 * Created by Matt on 3/10/2015.
 */
public class GetSampleImage_Result extends Result {
    private String url;

    @Override
    public String toString() {
        if(isError()){
            return super.toString();
        }else{
            return url;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
