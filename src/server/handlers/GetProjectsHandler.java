package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.ServerFacade;
import server.models.Project;
import shared.communication.GetProject_Result;
import shared.communication.GetProjects_Param;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Matt on 10/31/2014.
 */
public class GetProjectsHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        List<Project> projects =null;

        try{
            projects = ServerFacade.getProjects();
        }
        catch (exception e){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
        }

        GetProject_Result result = new GetProject_Result();
        result.setProjects(projects);
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
        xmlStream.toXML(result, httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();



    }
}
