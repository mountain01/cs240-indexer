package shared.Params;

/**
 * Created by Matt on 3/10/2015.
 */
public class Search_Params extends Params {
    private String[] searchValues;

    public String[] getSearchValues() {
        return searchValues;
    }

    public Search_Params(String username, String password, String[] searchValues, Integer[] fieldIds) {
        super(username, password);
        this.searchValues = searchValues;
        this.fieldIds = fieldIds;
    }

    public void setSearchValues(String[] searchValues) {
        this.searchValues = searchValues;
    }

    public Integer[] getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(Integer[] fieldIds) {
        this.fieldIds = fieldIds;
    }

    private Integer[] fieldIds;
}
