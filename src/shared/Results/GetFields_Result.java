package shared.Results;

import server.Models.Field;

import java.util.ArrayList;

/**
 * Created by Matt on 3/10/2015.
 */
public class GetFields_Result extends Result {
    private ArrayList<Field> fields;

    @Override
    public String toString() {
        if(isError()){
            return super.toString();
        } else {
            StringBuilder string = new StringBuilder();
            for(Field f:fields){
                string.append(f.getProjectid()).append("\n").append(f.getFieldid()).append("\n").append(f.getTitle()).append("\n");
            }
            return string.toString();
        }
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }
}
