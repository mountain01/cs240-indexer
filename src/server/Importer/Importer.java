package server.Importer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import server.DAOs.Database;
import server.Models.*;
import sun.text.resources.cldr.en.FormatData_en_IE;

import javax.swing.text.AbstractDocument;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matt on 3/9/2015.
 */
public class Importer {

    private Database database = new Database();

    public static void main (String[] args){

    }

    public void dataImport(String filename){
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            database.deleteData();
            database.startTransaction();
            File file = new File(filename);
            Document doc = builder.parse(file);
            parseUsers(doc.getElementsByTagName("user"));
            parseProjects(doc.getElementsByTagName("project"));
            database.endTransaction();

            copyFiles(file.getParentFile(),file.getParent());
        }catch(SAXException e){
            //e.printStackTrace();
        }catch(IOException e){
            //e.printStackTrace();
        }catch(ParserConfigurationException e){
            //e.printStackTrace();
        }
    }

    private void parseProjects(NodeList projectList) {
        for(int i =0;i<projectList.getLength();i++){
            Element projElem = (Element) projectList.item(i);
            String title = projElem.getElementsByTagName("title")
                    .item(0).getTextContent();
            String recordsPerImage = projElem.getElementsByTagName("recordsperimage")
                    .item(0).getTextContent();
            String firstYCoord = projElem.getElementsByTagName("firstycoord")
                    .item(0).getTextContent();
            String recordHeight = projElem.getElementsByTagName("recordheight")
                    .item(0).getTextContent();

            Project project = new Project();
            project.setTitle(title);
            project.setRecordheight(Integer.parseInt(recordHeight));
            project.setRecordsperimage(Integer.parseInt(recordsPerImage));
            project.setFirstycoord(Integer.parseInt(firstYCoord));
            int projectId = database.getProjectDAO().addProject(project).getProjectid();

            List<Field> fields = parseFields(projElem.getElementsByTagName("field"),projectId);
            parseBatches(projElem.getElementsByTagName("image"),projectId,fields);
        }
    }

    private void parseBatches(NodeList imageList, int projectid, List<Field> fields) {
        for(int i = 0;i<imageList.getLength();i++){
            Element batchElem = (Element)imageList.item(i);
            String file = batchElem.getElementsByTagName("file").item(0).getTextContent();

            Batch batch = new Batch();
            batch.setProjectid(projectid);
            batch.setImagefilepath(file);
            batch.setComplete(false);

            int id = database.getBatchDAO().addBatch(batch).getBatchid();
            parseRecords(batchElem.getElementsByTagName("record"),id,fields);
        }
    }

    private void parseRecords(NodeList recordList, int id, List<Field> fields) {
        for(int i = 0;i<recordList.getLength();i++){
            Element recordElem = (Element)recordList.item(i);
            Record record = new Record();
            Map<Field,String> newMap = new HashMap<Field,String>();
            NodeList valueList = recordElem.getElementsByTagName("value");
            for(int j = 0;j<valueList.getLength();j++){
                String value = valueList.item(j).getTextContent();
                newMap.put(fields.get(j),value);
            }
            record.setBatchid(id);
            record.setValues(newMap);
            record.setColid(i+1);
            database.getRecordDAO().addRecord(record);
        }
    }

    private List<Field> parseFields(NodeList fieldList,int projId) {
        List<Field> fields = new ArrayList<Field>();
        for(int i = 0;i<fieldList.getLength();i++){
            Element fieldElem = (Element) fieldList.item(i);
            String title = fieldElem.getElementsByTagName("title").item(0).getTextContent();
            String xcoord = fieldElem.getElementsByTagName("xcoord").item(0).getTextContent();
            String width = fieldElem.getElementsByTagName("width").item(0).getTextContent();
            Element helpHtml = (Element)fieldElem.getElementsByTagName("helphtml").item(0);
            Element knownData = (Element)fieldElem.getElementsByTagName("knowndata").item(0);
            String helphtml = helpHtml == null ? "" : helpHtml.getTextContent();
            String knowndata = knownData == null? "" : knownData.getTextContent();

            Field field = new Field();
            field.setTitle(title);
            field.setXcoord(Integer.parseInt(xcoord));
            field.setWidth(Integer.parseInt(width));
            field.setHelphtml(helphtml);
            field.setKnowndatahtml(knowndata);
            field.setProjectid(projId);
            field.setColid(i+1);

            fields.add(database.getFieldDAO().addField(field));
        }
        return fields;
    }

    private void parseUsers(NodeList userList){
        for(int i = 0; i < userList.getLength(); i++){
            Element userElem = (Element) userList.item(i);
            String username = userElem.getElementsByTagName("username")
                    .item(0).getTextContent();
            String password = userElem.getElementsByTagName("password")
                    .item(0).getTextContent();
            String firstName = userElem.getElementsByTagName("firstname")
                    .item(0).getTextContent();
            String lastName = userElem.getElementsByTagName("lastname")
                    .item(0).getTextContent();
            String email = userElem.getElementsByTagName("email")
                    .item(0).getTextContent();
            String indexedRecords = userElem.getElementsByTagName("indexedrecords")
                    .item(0).getTextContent();

            User user = new User(firstName,lastName,username,password,Integer.parseInt(indexedRecords),email);
            database.getUserDAO().addUser(user);
        }
    }


}
