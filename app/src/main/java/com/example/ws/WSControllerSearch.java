package com.example.ws;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class WSControllerSearch {

    boolean peerController(String ip, int port, WSReciver receiver) throws URISyntaxException {
        WebSocketClient client = new WSReciverWrapper(new URI("ws://" + ip + ":" + port), receiver);
        client.connect();
        return true;
    }


}
