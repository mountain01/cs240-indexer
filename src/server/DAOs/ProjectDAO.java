package server.DAOs;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import server.Models.Project;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class ProjectDAO {

    private Database database;

    public ProjectDAO(Database database) {
        this.database = database;
    }

    /**
     *
     * @return List of Projects
     */
    public ArrayList<Project> getProjects(){
        String query="SELECT * FROM project";
        Connection con = database.getConnection();
        ArrayList<Project> result = new ArrayList<Project>();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Project p = new Project();

                p.setProjectid(rs.getInt("projectid"));
                p.setFirstycoord(rs.getInt("firstycoord"));
                p.setRecordheight(rs.getInt("recordheight"));
                p.setRecordsperimage(rs.getInt("recordsperimage"));
                p.setTitle(rs.getString("title"));

                result.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Project getProjectById(int projid){
        String query="SELECT * FROM project WHERE projectid = ?";
        Connection con = database.getConnection();
        Project p = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,projid);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                p = new Project();
                p.setProjectid(rs.getInt("projectid"));
                p.setFirstycoord(rs.getInt("firstycoord"));
                p.setRecordheight(rs.getInt("recordheight"));
                p.setRecordsperimage(rs.getInt("recordsperimage"));
                p.setTitle(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     *
     * @param newProject
     */
    public Project addProject(Project newProject){
        String query = "INSERT INTO project (title,recordsperimage,recordheight,firstycoord) VALUES (?,?,?,?)";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,newProject.getTitle());
            statement.setInt(2, newProject.getRecordsperimage());
            statement.setInt(3, newProject.getRecordheight());
            statement.setInt(4,newProject.getFirstycoord());

            if (statement.executeUpdate() == 1) {
                Statement keyStmt = con.createStatement();
                ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
                newProject.setProjectid(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProject;
    };

    /**
     *
     * @param myProject
     */
    public void updateProject(Project myProject){
        String query = "UPDATE project SET title=?,recordsperimage=?,recordheight=?,firstycoord=? WHERE projectid=?";
        Connection con = database.getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,myProject.getTitle());
            statement.setInt(2, myProject.getRecordsperimage());
            statement.setInt(3, myProject.getRecordheight());
            statement.setInt(4,myProject.getFirstycoord());
            statement.setInt(5,myProject.getProjectid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    };

    public void deleteProject(Project myBatch){
        String query = "DELETE FROM project WHERE projectid = ?";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myBatch.getProjectid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
