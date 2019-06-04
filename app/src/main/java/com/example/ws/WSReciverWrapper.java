package com.example.ws;

import java.net.URI;

public class WSReciverWrapper extends WSClient {
    private WSReciver wsReciver;

    WSReciverWrapper(URI serverURI, WSReciver wsReciver) {
        super(serverURI);
        this.wsReciver = wsReciver;
    }
}
