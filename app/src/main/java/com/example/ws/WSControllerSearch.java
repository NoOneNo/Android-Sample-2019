package com.example.ws;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class WSControllerSearch {

    public static boolean peerController(String ip, int port, WSReciver receiver) {
        WebSocketClient client = null;
        try {
            client = new WSReciverWrapper(new URI("ws://" + ip + ":" + port), receiver);
            client.connect();
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }


}
