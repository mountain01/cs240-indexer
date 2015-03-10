package shared.Results;

import server.Models.User;

/**
 * Created by Matt on 3/10/2015.
 */
public class ValidateUser_Result extends Result {
    private User user;
    private boolean validUser;

    @Override
    public String toString() {
        if(isError()){
           return super.toString();
        } if (!validUser){
            return "FALSE\n";
        } else{
            return "TRUE\n"+user.getFirstname()+"\n"+user.getLastname()+"\n"+user.getRecordcount()+"\n";
        }
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
