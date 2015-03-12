package server.Models;

/**
 * Created by Matt on 10/14/2014.
 */
public class User {
    int userid;
    String firstname;
    String lastname;
    String username;
    String password;
    int recordcount;
    int currbatch;
    String email;

    public User() {

    }

    /**
     * get user id from User@return user id
     */
    public int getUserid() {
        return userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (currbatch != user.currbatch) return false;
        if (recordcount != user.recordcount) return false;
        if (userid != user.userid) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + recordcount;
        result = 31 * result + currbatch;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public User(String firstname, String lastname, String username, String password, int recordcount, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.recordcount = recordcount;
        this.email = email;
    }

    /**
     * sets user id for User
     * @param userid
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     *
     * @return first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * set first name for user
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * set user's last name
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * get user's username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * set user's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get user's password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * set user's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return number of completed records
     */
    public int getRecordcount() {
        return recordcount;
    }

    /**
     * set number of completed records for user
     * @param recordcount
     */
    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }

    /**
     *
     * @return current batch id
     */
    public int getCurrbatch() {
        return currbatch;
    }

    /**
     * set current batch for user
     * @param currbatch
     */
    public void setCurrbatch(int currbatch) {
        this.currbatch = currbatch;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email for user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
