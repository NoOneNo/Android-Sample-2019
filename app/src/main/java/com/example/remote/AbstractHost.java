package com.example.remote;

import android.content.Context;

import com.example.remote.Host;
import com.example.remote.event.TouchEvent;

public abstract class AbstractHost implements Host {
    @Override
    public void start(Context context) {

    }

    @Override
    public void touch(TouchEvent event) {
        send(event.toString());
    }

    @Override
    public void close() {

    }

    protected abstract void send(String msg);
}
