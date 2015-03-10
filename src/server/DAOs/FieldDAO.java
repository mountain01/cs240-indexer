package server.DAOs;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import server.Models.Field;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Matt on 10/22/2014.
 */
public class FieldDAO {

    private Database database;

    public FieldDAO(Database database) {
        this.database = database;
    }

    /**
     *
     * @return list of all fields
     */
    public ArrayList<Field> getFields(){
        ArrayList<Field> result = new ArrayList<Field>();
        String query="SELECT * FROM field";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Field f = new Field();

                f.setProjectid(rs.getInt("projectid"));
                f.setColid(rs.getInt("colid"));
                f.setFieldid(rs.getInt("fieldid"));
                f.setHelphtml(rs.getString("helphtml"));
                f.setKnowndatahtml(rs.getString("knowndatahtml"));
                f.setWidth(rs.getInt("width"));
                f.setTitle(rs.getString("title"));
                f.setXcoord(rs.getInt("xcoord"));

                result.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param newField
     */
    public Field addField(Field newField){
        String query = "INSERT INTO field (project,colid,helphtml,knowndatahtml,width,title,xcoord) VALUES (?,?,?,?,?,?,?)";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
             statement.setInt(1,newField.getProjectid());
            statement.setInt(2,newField.getColid());
            statement.setString(3,newField.getHelphtml());
            statement.setString(4,newField.getKnowndatahtml());
            statement.setInt(5,newField.getWidth());
            statement.setString(6,newField.getTitle());
            statement.setInt(7,newField.getXcoord());

            if (statement.executeUpdate() == 1) {
                Statement keyStmt = con.createStatement();
                ResultSet keyRS = keyStmt.executeQuery("select last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
                newField.setFieldid(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newField;
    };

    /**
     *
     * @param myField
     */
    public void updateField(Field myField){
        String query = "UPDATE field SET projectid=?,colid=?,helphtml=?,knowndatahtml=?,width=?,title=?,xcoord=? WHERE fieldid=?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,myField.getProjectid());
            statement.setInt(2,myField.getColid());
            statement.setString(3,myField.getHelphtml());
            statement.setString(4,myField.getKnowndatahtml());
            statement.setInt(5,myField.getWidth());
            statement.setString(6,myField.getTitle());
            statement.setInt(7,myField.getXcoord());
            statement.setInt(8,myField.getFieldid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };


    public void deleteField(Field myBatch){
        String query = "DELETE FROM field WHERE fieldid = ?";
        Connection con = null;
        try {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,myBatch.getFieldid());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
