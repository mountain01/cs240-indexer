package shared.Params;

/**
 * Created by Matt on 3/10/2015.
 */
public class Search_Params extends Params {
    private String[] searchValues;

    private String[] fieldIds;

    public String[] getSearchValues() {
        return searchValues;
    }

    public Search_Params(String username, String password, String[] searchValues, String[] fieldIds) {
        super(username, password);
        this.searchValues = searchValues;
        this.fieldIds = fieldIds;
    }

    public void setSearchValues(String[] searchValues) {
        this.searchValues = searchValues;
    }

    public String[] getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(String[] fieldIds) {
        this.fieldIds = fieldIds;
    }
}
