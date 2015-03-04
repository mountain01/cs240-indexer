package server.Models;

/**
 * Created by Matt on 10/14/2014.
 */
public class Project {
    int projectid;
    String title;
    int recordsperimage;
    int firstycoord;
    int recordheight;

    /**
     *
     * @return project id
     */
    public int getProjectid() {
        return projectid;
    }

    /**
     * set the project's id
     * @param projectid
     */
    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    /**
     *
     * @return project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * set title of the project
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return number of records per image
     */
    public int getRecordsperimage() {
        return recordsperimage;
    }

    /**
     * set number of records per image
     * @param recordsperimage
     */
    public void setRecordsperimage(int recordsperimage) {
        this.recordsperimage = recordsperimage;
    }

    /**
     *
     * @return y coordinate of first record
     */
    public int getFirstycoord() {
        return firstycoord;
    }

    /**
     * set first y coordinate of record in project
     * @param firstycoord
     */
    public void setFirstycoord(int firstycoord) {
        this.firstycoord = firstycoord;
    }

    /**
     *
     * @return height of each record
     */
    public int getRecordheight() {
        return recordheight;
    }

    /**
     * set height of records for project
     * @param recordheight
     */
    public void setRecordheight(int recordheight) {
        this.recordheight = recordheight;
    }
}
