package server.DAOTests;

import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import server.DAOs.Database;
import server.Models.User;

import java.util.ArrayList;

/**
 * Created by Matt on 3/11/2015.
 */
public class UserDAOTests {
    private Database db;
    private User user;

    @Before
    public void init(){
        db = new Database();
        db.deleteData();
        user = new User();
        user.setFirstname("Matt");
        user.setLastname("Edwards");
        user.setPassword("password");
        user.setUsername("username");
        user.setCurrbatch(5);
    }

    @Test
    public void getUsersTest(){
        ArrayList<User> users = new ArrayList<User>();
        Assert.assertSame(0, users.size());
        db.startTransaction();
        user = db.getUserDAO().addUser(user);
        user.setFirstname("Nathan");
        user.setLastname("Craven");
        user.setPassword("nope");
        user.setUsername("ncraven");
        User user2 = db.getUserDAO().addUser(user);
        user = db.getUserDAO().getUserById(1);
        users = db.getUserDAO().getUsers();
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(2,users.size());
        User f = users.get(0);
        User f2 = users.get(1);
        Assert.assertTrue(user.equals(f));
        Assert.assertTrue(user2.equals(f2));
    }

    @Test
    public void getUserByIdTest(){
        db.startTransaction();
        user = db.getUserDAO().addUser(user);
        User f = db.getUserDAO().getUserById(1);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(user.equals(f));
    }

    @Test
    public void checkUserTest(){
        db.startTransaction();
        user = db.getUserDAO().addUser(user);
        User user2 = db.getUserDAO().checkUser("username","password");
        Assert.assertTrue(user.equals(user2));
        user2 = db.getUserDAO().checkUser("","");
        Assert.assertNull(user2);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
    }

    @Test
    public void addUserTest(){
        db.startTransaction();
        user = db.getUserDAO().addUser(user);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1,user.getUserid());
    }


    @Test
    public void updateUserTest(){
        db.startTransaction();
        user = db.getUserDAO().addUser(user);
        user.setFirstname("Nathan");
        user.setLastname("Craven");
        user.setPassword("nope");
        user.setUsername("ncraven");
        db.getUserDAO().updateUser(user);
        User user2 = db.getUserDAO().getUserById(user.getUserid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(user.equals(user2));
        Assert.assertFalse("Matt".equals(user2.getFirstname()));
        Assert.assertFalse("Edwards".equals(user2.getLastname()));
        Assert.assertFalse("username".equals(user2.getUsername()));
        Assert.assertFalse("password".equals(user2.getPassword()));
        Assert.assertTrue("Nathan".equals(user2.getFirstname()));
        Assert.assertTrue("Craven".equals(user2.getLastname()));
        Assert.assertTrue("ncraven".equals(user2.getUsername()));
        Assert.assertTrue("nope".equals(user2.getPassword()));
    }

    @Test
    public void deleteUserTest(){
        db.startTransaction();
        user = db.getUserDAO().addUser(user);
        db.getUserDAO().deleteUser(user);
        User user2 = db.getUserDAO().getUserById(user.getUserid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertNull(user2);
    }
}

