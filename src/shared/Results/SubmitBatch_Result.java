package shared.Results;

/**
 * Created by Matt on 3/10/2015.
 */
public class SubmitBatch_Result extends Result {
    @Override
    public String toString(){
        if(isError()){
            return super.toString();
        } else {
            return "TRUE\n";
        }
    }
}
