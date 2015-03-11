package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.ServerFacade;
import shared.Results.ValidateUser_Result;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Matt on 10/31/2014.
 */
public class ValidateUserHandler implements HttpHandler {

    private XStream xmlStream = new XStream(new DomDriver());
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ServerFacade facade = new ServerFacade();
        String auth = httpExchange.getRequestHeaders().getFirst("authorization");
        ValidateUser_Result result = facade.validateUser(auth);
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        xmlStream.toXML(result,httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();
    }
}
