package shared.Results;

import server.Models.Project;

import java.util.ArrayList;

/**
 * Created by Matt on 3/10/2015.
 */
public class GetProjects_Result extends Result {
    ArrayList<Project> projects;

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        if(isError()){
            return super.toString();
        } else {
            StringBuilder string = new StringBuilder();
            for(Project p:projects){
                string.append(p.toString());
            }
            return string.toString();
        }
    }
}
