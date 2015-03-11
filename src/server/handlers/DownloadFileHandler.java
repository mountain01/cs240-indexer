package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Matt on 10/31/2014.
 */
public class DownloadFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ServerFacade facade = new ServerFacade();
        String uri = httpExchange.getRequestURI().toString();
        byte[] result = facade.downloadFile(uri);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        httpExchange.getResponseBody().write(result);
        httpExchange.getResponseBody().close();
    }
}
