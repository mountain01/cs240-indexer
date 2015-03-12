package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import server.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Matt on 10/31/2014.
 */
public class Server {

    private static int PORT_NUMBER = 8080;
    private static final int CONNECTIONS = 10;
    private HttpServer server;

    public Server(String[] args) {
        if(args.length >= 1){
            PORT_NUMBER = Integer.parseInt(args[0]);
        }
    }

    private void run(){
        try {
            server = HttpServer.create(new InetSocketAddress(PORT_NUMBER),CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.setExecutor(null); // use the default executor

        server.createContext("/ValidateUser",validateUserHandler);
        server.createContext("/GetProject", getProjectHandler);
        server.createContext("/GetSampleImage",getSampleImageHandler);
        server.createContext("/DownloadFile",downloadFileHandler);
        server.createContext("/DownloadBatch",downloadBatchHandler);
        server.createContext("/Search",searchHandler);
        server.createContext("/SubmitBatch",submitBatchHandler);
        server.createContext("/GetFields",getFieldsHandler);

        server.start();

    }

    private HttpHandler validateUserHandler = new ValidateUserHandler();
    private HttpHandler getProjectHandler = new GetProjectsHandler();
    private HttpHandler getSampleImageHandler = new GetSampleImageHandler();
    private HttpHandler downloadBatchHandler = new DownloadBatchHandler();
    private HttpHandler downloadFileHandler = new DownloadFileHandler();
    private HttpHandler submitBatchHandler = new SubmitBatchHandler();
    private HttpHandler searchHandler = new SearchHandler();
    private HttpHandler getFieldsHandler = new GetFieldsHandler();

    public static void main(String[] arg){new Server(arg).run();};

    private static Server testServer;

    public static void startTest(){
        String[] args = {"34590"};
        testServer = new Server(args);
        testServer.run();
    }

    public static void endTest(){
        testServer.server.stop(10);
    }

}
