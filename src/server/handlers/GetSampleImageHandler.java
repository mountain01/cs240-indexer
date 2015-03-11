package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import server.ServerFacade;
import shared.Results.DownloadBatch_Result;
import shared.Results.GetSampleImage_Result;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Matt on 10/31/2014.
 */
public class GetSampleImageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ServerFacade facade = new ServerFacade();
        XStream xmlStream = new XStream(new DomDriver());
        String auth = httpExchange.getRequestHeaders().getFirst("authorization");
        String uri = httpExchange.getRequestURI().toString();
        String[] splitURI = uri.split("/");
        GetSampleImage_Result result = new GetSampleImage_Result();
        if(splitURI.length < 2){
            result.setError(true);
        } else {
            result = facade.getSampleImage(auth,Integer.parseInt(splitURI[splitURI.length-1]));
        }
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        xmlStream.toXML(result,httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();
    }
}
