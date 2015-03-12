package server.DAOTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.DAOs.Database;
import server.Models.Project;

import java.util.ArrayList;

/**
 * Created by Matt on 3/11/2015.
 */
public class ProjectDAOTests {
    private Database db;
    private Project project;

    @Before
    public void initTest(){
        db = new Database();
        db.deleteData();
        project = new Project();
        project.setTitle("Matt");
        project.setFirstycoord(20);
        project.setRecordsperimage(10);
        project.setRecordheight(5);
    }

    @Test
    public void getProjectsTest(){
        ArrayList<Project> projects = new ArrayList<Project>();
        Assert.assertSame(0,projects.size());
        db.startTransaction();
        project = db.getProjectDAO().addProject(project);
        project.setTitle("bob");
        project.setRecordheight(15);
        Project project2 = db.getProjectDAO().addProject(project);
        project = db.getProjectDAO().getProjectById(1);
        projects = db.getProjectDAO().getProjects();
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(2,projects.size());
        Project f = projects.get(0);
        Project f2 = projects.get(1);
        Assert.assertTrue(project.equals(f));
        Assert.assertTrue(project2.equals(f2));
    }

    @Test
    public void getProjectByIdTest(){
        db.startTransaction();
        project = db.getProjectDAO().addProject(project);
        Project f = db.getProjectDAO().getProjectById(1);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(project.equals(f));
    }

    @Test
    public void addProjectTest(){
        db.startTransaction();
        project = db.getProjectDAO().addProject(project);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1,project.getProjectid());
    }

    @Test
    public void updateProjectTest(){
        db.startTransaction();
        project = db.getProjectDAO().addProject(project);
        project.setTitle("bob");
        project.setRecordsperimage(50);
        db.getProjectDAO().updateProject(project);
        Project project2 = db.getProjectDAO().getProjectById(project.getProjectid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(project.equals(project2));
        Assert.assertNotSame("Matt", project2.getTitle());
        Assert.assertNotSame(10, project2.getRecordsperimage());
        Assert.assertTrue("bob".equals(project2.getTitle()));
        Assert.assertSame(50,project2.getRecordsperimage());
    }

    @Test
    public void deleteProjectTest(){
        db.startTransaction();
        project = db.getProjectDAO().addProject(project);
        db.getProjectDAO().deleteProject(project);
        Project project2 = db.getProjectDAO().getProjectById(project.getProjectid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertNull(project2);
    }
}
