package server.DAOs;

import server.Models.Record;
import server.Models.SearchResult;
import server.Models.Value;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class ValueDAO {

    private Database database;

    public ValueDAO(Database database) {
        this.database = database;
    }

    /**
     *
     * @return list of all values
     */
    public ArrayList<Value> getValues(){
        ArrayList<Value> result = new ArrayList<Value>();
        Connection con = null;
        PreparedStatement statement = null;

        try {
            String query = "Select * from Value";
            statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Value v = new Value();
                v.setName(rs.getString("value"));
                v.setFieldid(rs.getInt("fieldid"));
                v.setValueid(rs.getInt("valueid"));
                v.setRecordid(rs.getInt("recordid"));

                result.add(v);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param newValue
     */
    public Value addValue(Value newValue){
        Connection con = null;
        String query = "INSERT INTO value (recordid,name,fieldid) VALUES (?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,newValue.getRecordid());
            statement.setString(2,newValue.getName());
            statement.setInt(3,newValue.getFieldid());

            if (statement.executeUpdate() == 1) {
                Statement keyStmt = con.createStatement();
                ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
                newValue.setValueid(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newValue;
    };

    /**
     *
     * @param myValue
     */
    public void updateValue(Value myValue){
        String query="UPDATE value SET fieldid=?,name=?,recordid=? WHERE valueid = ?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,myValue.getFieldid());
            statement.setString(2,myValue.getName());
            statement.setInt(3,myValue.getRecordid());
            statement.setInt(4, myValue.getValueid());

            statement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    };


    public void deleteValue(Value myBatch){
        String query = "DELETE FROM value WHERE valueid = ?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myBatch.getValueid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SearchResult> searchValues(int fieldid, String[] values){
        String query = "SELECT * from value WHERE fieldid = ? and (";
        for(int i = 0;i<values.length;i++){
            query += "name LIKE ? ";
            if(i != values.length-1){
                query+=" OR ";
            }
        }
        query+=")";
        Connection con = null;
        ArrayList<SearchResult>result = new ArrayList<SearchResult>();
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,fieldid);
            for(int i = 0;i<values.length;i++){
                statement.setString(i+2,values[i]);
            }

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                SearchResult sr = new SearchResult();
                Record r = database.getRecordDAO().getRecordsByRecordId(rs.getInt("recordid"));
                sr.setFieldid(fieldid);
                sr.setBatchid(r.getBatchid());
                sr.setRecordNumber(r.getColid());

                result.add(sr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
