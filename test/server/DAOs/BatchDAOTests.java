import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.DAOs.Database;
import server.Models.Batch;

import java.util.ArrayList;

/**
 * Created by Matt on 3/11/2015.
 */
public class BatchDAOTests {
    private Database db;
    private Batch batch;

    @Before
    public void beforeStuff() {
        db = new Database();
        db.deleteData();
        batch = new Batch();
        batch.setComplete(false);
        batch.setProjectid(5);
        batch.setImagefilepath("oldPath");
    }

    @After
    public void cleanUP(){
        db.deleteData();
    }

    @Test
    public void addBatchTest() {
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
    }

    @Test
    public void getBatchesByProjectIDTest() {
        ArrayList<Batch> batches = new ArrayList<Batch>();
        Assert.assertSame(0, batches.size());
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        batches = db.getBatchDAO().getBatchesByProjectID(batch.getProjectid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(1, batches.size());
        Batch batch2 = batches.get(0);
        Assert.assertTrue(batch.equals(batch2));
    }

    @Test
    public void setBatchCompleteTest() {
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        db.getBatchDAO().setBatchComplete(batch.getBatchid());
        Batch batch2 = db.getBatchDAO().getBatchById(batch.getBatchid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertFalse(batch.isComplete());
        Assert.assertSame(batch.getBatchid(), batch2.getBatchid());
        Assert.assertTrue(batch2.isComplete());
    }


    @Test
    public void getBatchesTest() {
        ArrayList<Batch> batches = new ArrayList<Batch>();
        Assert.assertSame(0, batches.size());
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        batch.setImagefilepath("newPath");
        batch.setComplete(true);
        Batch batch2 = db.getBatchDAO().addBatch(batch);
        batch = db.getBatchDAO().getBatchById(1);
        batches = db.getBatchDAO().getBatches();
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertSame(2, batches.size());
        Batch batch3 = batches.get(0);
        Batch batch4 = batches.get(1);
        Assert.assertTrue(batch.equals(batch3));
        Assert.assertTrue(batch2.equals(batch4));
    }

    @Test
    public void getBatchByIdTest() {
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        Batch batch2 = db.getBatchDAO().getBatchById(batch.getBatchid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(batch.equals(batch2));
    }

    @Test
    public void updateBatchTest() {
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        batch.setComplete(true);
        int newProjId = 20;
        batch.setProjectid(newProjId);
        batch.setImagefilepath("newpath");
        db.getBatchDAO().updateBatch(batch);
        Batch batch2 = db.getBatchDAO().getBatchById(batch.getBatchid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertTrue(batch.equals(batch2));
        Assert.assertNotSame("oldPath", batch2.getImagefilepath());
        Assert.assertTrue("newpath".equals(batch2.getImagefilepath()));
    }

    @Test
    public void deleteBatchTest() {
        db.startTransaction();
        batch = db.getBatchDAO().addBatch(batch);
        db.getBatchDAO().deleteBatch(batch);
        Batch batch2 = db.getBatchDAO().getBatchById(batch.getBatchid());
        db.endTransaction();
        Assert.assertTrue(db.wasSuccesful());
        Assert.assertNull(batch2);

    }
}
