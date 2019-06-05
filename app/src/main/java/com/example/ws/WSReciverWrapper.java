package com.example.ws;

import com.example.ws.event.Event;
import com.example.ws.event.EventParser;
import com.example.ws.event.TouchEvent;

import java.net.URI;

public class WSReciverWrapper extends WSClient {
    private WSReciver wsReciver;

    WSReciverWrapper(URI serverURI, WSReciver wsReciver) {
        super(serverURI);
        this.wsReciver = wsReciver;
    }

    @Override
    public void onMessage(String message) {
        super.onMessage(message);

        Event event = EventParser.parseEvent(message);
        if (event instanceof TouchEvent) {
            wsReciver.onTouch((TouchEvent) event);
        }
    }
}
