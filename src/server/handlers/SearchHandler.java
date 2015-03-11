package server.handlers;

import com.sun.deploy.util.SearchPath;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.ServerFacade;
import shared.Params.Search_Params;
import shared.Params.SubmitBatch_Params;
import shared.Results.Search_Result;
import shared.Results.SubmitBatch_Result;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Matt on 10/31/2014.
 */
public class SearchHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ServerFacade facade = new ServerFacade();
        XStream xmlStream = new XStream(new DomDriver());
        String auth = httpExchange.getRequestHeaders().getFirst("authorization");
        Search_Params params = (Search_Params) xmlStream.fromXML(httpExchange.getRequestBody());
        Search_Result result = facade.search(auth, params);
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        xmlStream.toXML(result,httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();
    }
}
