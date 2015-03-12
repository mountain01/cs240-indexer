package server.Models;

/**
 * Created by Matt on 10/14/2014.
 */
public class Field {
    int fieldid;
    int projectid;
    String title;
    int xcoord;
    int width;
    String helphtml;
    String knowndatahtml;
    int colid;

    /**
     *
     * @return field id
     */
    public int getFieldid() {
        return fieldid;
    }

    /**
     * set field's id
     * @param fieldid
     */
    public void setFieldid(int fieldid) {
        this.fieldid = fieldid;
    }

    @Override
    public String toString() {
        return fieldid+"\n"+colid+"\n"+title+"\n"+helphtml+"\n"+xcoord+"\n"+width+"\n"+knowndatahtml+"\n";
    }

    /**
     *
     * @return field title
     */
    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;

        Field field = (Field) o;

        if (colid != field.colid) return false;
        if (fieldid != field.fieldid) return false;
        if (projectid != field.projectid) return false;
        if (width != field.width) return false;
        if (xcoord != field.xcoord) return false;
        if (helphtml != null ? !helphtml.equals(field.helphtml) : field.helphtml != null) return false;
        if (knowndatahtml != null ? !knowndatahtml.equals(field.knowndatahtml) : field.knowndatahtml != null)
            return false;
        if (title != null ? !title.equals(field.title) : field.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fieldid;
        result = 31 * result + projectid;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + xcoord;
        result = 31 * result + width;
        result = 31 * result + (helphtml != null ? helphtml.hashCode() : 0);
        result = 31 * result + (knowndatahtml != null ? knowndatahtml.hashCode() : 0);
        result = 31 * result + colid;
        return result;
    }

    /**
     *
     * @return associated program id
     */
    public int getProjectid() {
        return projectid;
    }

    /**
     * set field's associated batch id
     * @param projectid
     */
    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    /**
     * set field's title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return field's x coordinate
     */
    public int getXcoord() {
        return xcoord;
    }

    /**
     * set field's x coordinate
     * @param xcoord
     */
    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    /**
     *
     * @return fields width
     */
    public int getWidth() {
        return width;
    }

    /**
     * set the field's width
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return fields help text url
     */
    public String getHelphtml() {
        return helphtml;
    }

    /**
     * set field's help html
     * @param helphtml
     */
    public void setHelphtml(String helphtml) {
        this.helphtml = helphtml;
    }

    /**
     * g
     * @return fields known values url
     */
    public String getKnowndatahtml() {
        return knowndatahtml;
    }

    /**
     * set field's known data html
     * @param knowndatahtml
     */
    public void setKnowndatahtml(String knowndatahtml) {
        this.knowndatahtml = knowndatahtml;
    }

    /**
     *
     * @return field's column id
     */
    public int getColid() {
        return colid;
    }

    /**
     * set the field's column id
     * @param colid
     */
    public void setColid(int colid) {
        this.colid = colid;
    }
}
