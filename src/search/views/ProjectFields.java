package search.views;

import server.Models.Field;
import server.Models.Project;

import java.util.List;

/**
 * Created by Matt on 3/25/2015.
 */
public class ProjectFields {
    private List<Field> fields;

    public ProjectFields(List<Field> fields, Project project) {
        this.fields = fields;
        this.project = project;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    private Project project;
}
