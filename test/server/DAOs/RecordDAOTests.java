import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.DAOs.Database;
import server.Models.Record;

import java.util.ArrayList;

/**
 * Created by Matt on 3/11/2015.
 */
public class RecordDAOTests {
    private Database db;
    private Record record;
    
    @Before
    public void init(){
        db = new Database();
        db.deleteData();
        record = new Record();
        record.setBatchid(5);
        record.setColid(5);
    }

    @Test
    public void getRecordsTest(){
        ArrayList<Record> records = new ArrayList<Record>();
        Assert.assertSame(0, records.size());
        db.startTransaction();
        record = db.getRecordDAO().addRecord(record);
        record.setBatchid(10);
        record.setColid(15);
        Record record2 = db.getRecordDAO().addRecord(record);
        record = db.getRecordDAO().getRecordsByRecordId(1);
        records = db.getRecordDAO().getRecords();
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(2,records.size());
        Record f = records.get(0);
        Record f2 = records.get(1);
        Assert.assertTrue(record.equals(f));
        Assert.assertTrue(record2.equals(f2));
    }

    @Test
    public void getRecordsByRecordIdTest(){
        db.startTransaction();
        record = db.getRecordDAO().addRecord(record);
        Record f = db.getRecordDAO().getRecordsByRecordId(1);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(record.equals(f));
    }

    @Test
    public void addRecordTest(){
        db.startTransaction();
        record = db.getRecordDAO().addRecord(record);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1,record.getRecordid());
    }

    @Test
    public void getRecordsByBatchIdTest(){
        ArrayList<Record> records = new ArrayList<Record>();
        Assert.assertSame(0, records.size());
        db.startTransaction();
        record = db.getRecordDAO().addRecord(record);
        records = db.getRecordDAO().getRecordsByBatchId(record.getBatchid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1, records.size());
        Record record2 = records.get(0);
        Assert.assertTrue(record.equals(record2));
    }

    @Test
    public void updateRecordTest(){
        db.startTransaction();
        record = db.getRecordDAO().addRecord(record);
        record.setBatchid(10);
        record.setColid(15);
        db.getRecordDAO().updateRecord(record);
        Record record2 = db.getRecordDAO().getRecordsByRecordId(record.getRecordid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(record.equals(record2));
        Assert.assertNotSame(5, record2.getBatchid());
        Assert.assertNotSame(5, record2.getColid());
        Assert.assertSame(10,record2.getBatchid());
        Assert.assertSame(15,record2.getColid());
    }

    @Test
    public void deleteRecordTest(){
        db.startTransaction();
        record = db.getRecordDAO().addRecord(record);
        db.getRecordDAO().deleteRecord(record);
        Record record2 = db.getRecordDAO().getRecordsByRecordId(record.getRecordid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertNull(record2);
    }
}
