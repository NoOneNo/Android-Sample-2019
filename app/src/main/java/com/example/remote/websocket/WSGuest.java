package com.example.remote.websocket;

import com.example.remote.AbstractGuest;
import com.example.remote.Console;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WSGuest extends AbstractGuest {
    private WSClient wsClient;

    public void connect(URI serverURI) {
        wsClient = new WSClient(serverURI) {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                onMsg(message);
            }

            @Override
            public void onOpen(ServerHandshake handshake) {
                super.onOpen(handshake);
                log("new connection opened");
            }
        };
    }
}
