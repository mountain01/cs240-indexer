import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.DAOs.Database;
import server.Models.Field;
import server.Models.Field;

import java.util.ArrayList;

/**
 * Created by Matt on 3/11/2015.
 */
public class FieldDAOTests {
    private Database db;
    private Field field;
    
    @Before
    public void init(){
        db = new Database();
        db.deleteData();
        field = new Field();
        field.setKnowndatahtml("knowndata");
        field.setHelphtml("helpdata");
        field.setProjectid(10);
        field.setTitle("myField");
    }
    
    @Test
    public void getFields(){
        ArrayList<Field> fields = new ArrayList<Field>();
        Assert.assertSame(0,fields.size());
        db.startTransaction();
        field = db.getFieldDAO().addField(field);
        field.setKnowndatahtml("bob");
        field.setHelphtml("jack");
        Field field2 = db.getFieldDAO().addField(field);
        field = db.getFieldDAO().getFieldById(1);
        fields = db.getFieldDAO().getFields();
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(2,fields.size());
        Field f = fields.get(0);
        Field f2 = fields.get(1);
        Assert.assertTrue(field.equals(f));
        Assert.assertTrue(field2.equals(f2));
    }

    @Test
    public void getFieldsbyProjectId(){
        ArrayList<Field> fields = new ArrayList<Field>();
        Assert.assertSame(0, fields.size());
        db.startTransaction();
        field = db.getFieldDAO().addField(field);
        fields = db.getFieldDAO().getFieldsbyProjectId(field.getProjectid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1, fields.size());
        Field field2 = fields.get(0);
        Assert.assertTrue(field.equals(field2));
    }

    @Test
    public void addField(){
        db.startTransaction();
        field = db.getFieldDAO().addField(field);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1,field.getFieldid());
    }

    @Test
    public void updateField(){
        db.startTransaction();
        field = db.getFieldDAO().addField(field);
        int newProjId = 20;
        field.setProjectid(newProjId);
        field.setKnowndatahtml("newknownpath");
        field.setHelphtml("newhelppath");
        db.getFieldDAO().updateField(field);
        Field field2 = db.getFieldDAO().getFieldById(field.getFieldid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(field.equals(field2));
        Assert.assertNotSame("helpdata", field2.getHelphtml());
        Assert.assertNotSame("knowndata", field2.getKnowndatahtml());
        Assert.assertTrue("newknownpath".equals(field2.getKnowndatahtml()));
        Assert.assertTrue("newhelppath".equals(field2.getHelphtml()));
    }

    @Test
    public void deleteFieldTest() {
        db.startTransaction();
        field = db.getFieldDAO().addField(field);
        db.getFieldDAO().deleteField(field);
        Field field2 = db.getFieldDAO().getFieldById(field.getFieldid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertNull(field2);
    }
}
