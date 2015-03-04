package server.DAOs;

import server.Models.Value;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class ValueDAO {

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
    public void addValue(Value newValue){
        Connection con = null;
        String query = "INSERT INTO value (valueid,recordid,name,fieldid) VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,newValue.getValueid());
            statement.setInt(2,newValue.getRecordid());
            statement.setString(3,newValue.getName());
            statement.setInt(4,newValue.getFieldid());

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
}
