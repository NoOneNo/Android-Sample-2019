package com.example.remote;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class WSHelper {

    public static boolean peerController(String ip, int port, WSReceiver receiver) {
        WebSocketClient client = null;
        try {
            client = new WSReceiverWrapper(new URI("ws://" + ip + ":" + port), receiver);
            client.connect();
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return false;
    }
}
