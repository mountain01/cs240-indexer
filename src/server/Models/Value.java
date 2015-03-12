package server.Models;

/**
 * Created by Matt on 10/14/2014.
 */
public class Value {
    int valueid;
    String name;
    int recordid;
    int fieldid;

    /**
     *
     * @return value id
     */
    public int getValueid() {
        return valueid;
    }

    /**
     * Sets the valueid of the value
     * @param valueid
     */
    public void setValueid(int valueid) {
        this.valueid = valueid;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of Value
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return associated record id
     */
    public int getRecordid() {
        return recordid;
    }

    /**
     * set Recordid of Value
     * @param recordid
     */
    public void setRecordid(int recordid) {
        this.recordid = recordid;
    }

    /**
     *
     * @return field id
     */
    public int getFieldid() {
        return fieldid;
    }

    /**
     * set field id of Value
     * @param fieldid
     */
    public void setFieldid(int fieldid) {
        this.fieldid = fieldid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value)) return false;

        Value value = (Value) o;

        if (fieldid != value.fieldid) return false;
        if (recordid != value.recordid) return false;
        if (valueid != value.valueid) return false;
        if (name != null ? !name.equals(value.name) : value.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = valueid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + recordid;
        result = 31 * result + fieldid;
        return result;
    }
}
