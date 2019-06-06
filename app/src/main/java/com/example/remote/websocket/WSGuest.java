package com.example.remote.websocket;

import android.os.Handler;
import android.os.Looper;
import com.example.remote.Console;
import com.example.remote.Guest;
import com.example.remote.GuestAdapter;
import com.example.remote.event.Event;
import com.example.remote.event.EventParser;
import com.example.remote.event.TouchEvent;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WSGuest extends WSClient implements Guest {

    private GuestAdapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());

    private Console console;

    public WSGuest(URI serverURI, Console console) {
        super(serverURI);
        this.console = console;
    }

    public void setAdapter(GuestAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onMessage(String message) {
        super.onMessage(message);

        Event event = EventParser.parseEvent(message);
        if (event instanceof TouchEvent) {
            onTouch((TouchEvent) event);
        }
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        super.onOpen(handshake);

        log("new connection opened");
    }

    @Override
    public void onTouch(final TouchEvent event) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.onTouch(event);
            }
        });
    }

    private void log(String text) {
        console.log(text);
    }
}
