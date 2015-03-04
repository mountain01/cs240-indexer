package server.DAOs;

import server.Models.Batch;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class BatchDAO {
    /**
     *
     * @return list of all batches
     */
    public ArrayList<Batch> getBatches(){
        ArrayList<Batch> result= new ArrayList<Batch>();
        String query="SELECT * FROM batch";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Batch b = new Batch();
                b.setBatchid(rs.getInt("batchid"));
                b.setProjectid(rs.getInt("projectid"));
                b.setImagefilepath(rs.getString("imagefilepath"));
                b.setComplete(rs.getBoolean("complete"));

                result.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param newBatch
     */
    public void addBatch (Batch newBatch){
        String query = "INSERT INTO batch (imagefilepath,complete,projectid) VALUES (?,?,?)";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1,newBatch.getImagefilepath());
            statement.setBoolean(2,newBatch.isComplete());
            statement.setInt(3,newBatch.getProjectid());

            if (statement.executeUpdate() == 1) {
                Statement keyStmt = con.createStatement();
                ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
                newBatch.setBatchid(id);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    /**
     *
     * @param myBatch
     */
    public void updateBatch(Batch myBatch){
        String query = "UPDATE batch SET projectid=?,imagefilepath=?,coplete=? WHERE batchid=?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myBatch.getProjectid());
            statement.setString(2,myBatch.getImagefilepath());
            statement.setBoolean(3,myBatch.isComplete());
            statement.setInt(4,myBatch.getBatchid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    /**
     *
     * @param myBatch
     */
    public void deleteBatch(Batch myBatch){
        String query = "DELETE FROM batch WHERE batchid = ?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myBatch.getBatchid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
