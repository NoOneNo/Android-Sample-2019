package com.example.remote;

import com.example.remote.base.WSClient;
import com.example.remote.event.Event;
import com.example.remote.event.EventParser;
import com.example.remote.event.TouchEvent;

import java.net.URI;

public class WSReceiverWrapper extends WSClient {
    private WSReceiver wsReceiver;

    WSReceiverWrapper(URI serverURI, WSReceiver wsReceiver) {
        super(serverURI);
        this.wsReceiver = wsReceiver;
    }

    @Override
    public void onMessage(String message) {
        super.onMessage(message);

        Event event = EventParser.parseEvent(message);
        if (event instanceof TouchEvent) {
            wsReceiver.onTouch((TouchEvent) event);
        }
    }
}
