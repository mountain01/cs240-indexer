package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.ServerFacade;
import shared.Results.DownloadBatch_Result;
import shared.Results.GetProjects_Result;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Matt on 10/31/2014.
 */
public class GetProjectsHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ServerFacade facade = new ServerFacade();
        XStream xmlStream = new XStream(new DomDriver());
        String auth = httpExchange.getRequestHeaders().getFirst("authorization");
        GetProjects_Result result = facade.getProjects(auth);
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        xmlStream.toXML(result,httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();
    }
}
