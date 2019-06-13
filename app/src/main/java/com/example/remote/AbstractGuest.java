package com.example.remote;

import android.os.Handler;
import android.os.Looper;

import com.example.remote.event.Event;
import com.example.remote.event.EventParser;
import com.example.remote.event.TouchEvent;

public abstract class AbstractGuest implements Guest {

    private GuestAdapter adapter;
    private Console console;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onTouch(TouchEvent event) {
        adapter.onTouch(event);
    }

    public void onMsg(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Event event = EventParser.parseEvent(message);
                if (event instanceof TouchEvent) {
                    onTouch((TouchEvent) event);
                }
            }
        });
    }

    public void setAdapter(GuestAdapter adapter) {
        this.adapter = adapter;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    protected void log(String msg) {
        console.log(msg);
    }
}
