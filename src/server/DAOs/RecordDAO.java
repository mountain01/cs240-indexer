package server.DAOs;

import server.Models.Field;
import server.Models.Record;
import server.Models.Value;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class RecordDAO {

    private Database database;

    public RecordDAO(Database database) {
        this.database = database;
    }

    /**
     *
     * @return list of all records
     */
    public ArrayList<Record> getRecords(){

        ArrayList<Record> result = new ArrayList<Record>();
        String query = "SELECT * FROM record";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Record r = new Record();

                r.setBatchid(rs.getInt("batchid"));
                r.setRecordid(rs.getInt("recordid"));
                r.setColid(rs.getInt("colid"));

                result.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Record> getRecordsByBatchId(int batchid){
        ArrayList<Record> result = new ArrayList<Record>();
        String query = "SELECT * FROM record WHERE batchid = ?";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,batchid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Record r = new Record();

                r.setBatchid(rs.getInt("batchid"));
                r.setRecordid(rs.getInt("recordid"));
                r.setColid(rs.getInt("colid"));

                result.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Record getRecordsByRecordId(int recordid){
        Record r = null;
        String query = "SELECT * FROM record WHERE recordid = ?";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,recordid);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                r = new Record();

                r.setBatchid(rs.getInt("batchid"));
                r.setRecordid(rs.getInt("recordid"));
                r.setColid(rs.getInt("colid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     *
     * @param newRecord
     */
    public Record addRecord(Record newRecord){
        String query = "INSERT INTO record (batchid,colid) VALUES (?,?)";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,newRecord.getBatchid());
            statement.setInt(2,newRecord.getColid());

            if (statement.executeUpdate() == 1) {
                Statement keyStmt = con.createStatement();
                ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
                newRecord.setRecordid(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Field field :newRecord.getValues().keySet()){
            Value value = new Value();
            value.setFieldid(field.getFieldid());
            value.setRecordid(newRecord.getRecordid());
            value.setName(newRecord.getValues().get(field));
            database.getValueDAO().addValue(value);
        }
        return newRecord;
    };

    /**
     *
     * @param myRecord
     */
    public void updateRecord(Record myRecord){
        String query = "UPDATE record SET batchid=? WHERE recordid=?";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myRecord.getBatchid());
            statement.setInt(2,myRecord.getRecordid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public void deleteRecord(Record myBatch){
        String query = "DELETE FROM record WHERE recordid = ?";
        Connection con = database.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myBatch.getRecordid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
