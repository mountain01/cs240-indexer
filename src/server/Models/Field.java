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
        return fieldid+"\n"+title+"\n"+helphtml+"\n"+xcoord+"\n"+width+"\n"+knowndatahtml+"\n";
    }

    /**
     *
     * @return field title
     */
    public String getTitle() {
        return title;
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
