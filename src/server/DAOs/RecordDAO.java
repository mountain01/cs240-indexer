package server.DAOs;

import server.Models.Record;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class RecordDAO {
    /**
     *
     * @return list of all records
     */
    public ArrayList<Record> getRecords(){

        ArrayList<Record> result = new ArrayList<Record>();
        String query = "SELECT * FROM record";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Record r = new Record();

                r.setBatchid(rs.getInt("batchid"));
                r.setRecordid(rs.getInt("recordid"));

                result.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param newRecord
     */
    public void addRecord(Record newRecord){
        String query = "INSERT INTO record (batchid) VALUES (?)";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,newRecord.getBatchid());

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
    };

    /**
     *
     * @param myRecord
     */
    public void updateRecord(Record myRecord){
        String query = "UPDATE record SET batchid=? WHERE recordid=?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myRecord.getBatchid());
            statement.setInt(2,myRecord.getRecordid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}
