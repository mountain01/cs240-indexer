package server.DAOTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.DAOs.Database;
import server.Models.Value;

import java.util.ArrayList;

/**
 * Created by Matt on 3/12/2015.
 */
public class ValueDAOTests {
    private Database db;
    private Value value;

    @Before
    public void init(){
        db = new Database();
        db.deleteData();
        value = new Value();
        value.setName("Matt");
        value.setRecordid(5);
        value.setFieldid(5);
    }

    @Test
    public void getValuesTest(){
        ArrayList<Value> values = new ArrayList<Value>();
        Assert.assertSame(0, values.size());
        db.startTransaction();
        value = db.getValueDAO().addValue(value);
        value.setName("Zack");
        value.setRecordid(10);
        value.setFieldid(10);
        Value value2 = db.getValueDAO().addValue(value);
        value = db.getValueDAO().getValueById(1);
        values = db.getValueDAO().getValues();
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(2,values.size());
        Value f = values.get(0);
        Value f2 = values.get(1);
        Assert.assertTrue(value.equals(f));
        Assert.assertTrue(value2.equals(f2));
    }

    @Test
    public void getValueByIdTest(){
        db.startTransaction();
        value = db.getValueDAO().addValue(value);
        Value f = db.getValueDAO().getValueById(1);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(value.equals(f));
    }

    @Test
    public void addValueTest(){
        db.startTransaction();
        value = db.getValueDAO().addValue(value);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1,value.getValueid());
    }


    @Test
    public void updateValueTest(){
        db.startTransaction();
        value = db.getValueDAO().addValue(value);
        value.setName("Zack");
        value.setRecordid(10);
        value.setFieldid(10);
        db.getValueDAO().updateValue(value);
        Value value2 = db.getValueDAO().getValueById(value.getValueid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(value.equals(value2));
        Assert.assertFalse("Matt".equals(value2.getName()));
        Assert.assertNotSame(5,value2.getFieldid());
        Assert.assertNotSame(5,value2.getRecordid());
        Assert.assertTrue("Zack".equals(value2.getName()));
        Assert.assertSame(10,value2.getFieldid());
        Assert.assertSame(10,value2.getRecordid());
    }

    @Test
    public void deleteValueTest(){
        db.startTransaction();
        value = db.getValueDAO().addValue(value);
        db.getValueDAO().deleteValue(value);
        Value value2 = db.getValueDAO().getValueById(value.getValueid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertNull(value2);
    }
}


