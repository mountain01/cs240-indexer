package client;

import shared.Communicator.ClientCommunicator;

import java.awt.*;

/**
 * Created by Matt on 4/8/2015.
 */
public class IndexerGUI {

    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        ClientCommunicator.getInstance().initialize(host,port);
        IndexerFrame frame = new IndexerFrame();
        frame.start();
    }
}
