package com.example.ws;

import android.os.Handler;
import android.os.HandlerThread;

import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WSThread extends HandlerThread {

    private Handler handler;
    private WebSocketServer server;

    public WSThread(String name) {
        super(name);
    }

    @Override
    public synchronized void start() {
        super.start();
        handler = new Handler(getLooper());
    }

    public void startServer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                server = new WSServer(new InetSocketAddress("10.232.60.8", 8887));
                server.start();
            }
        });
    }

    public void stopServer() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (server == null) {
                    return;
                }

                try {
                    server.stop();
                    server = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void broadcast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                server.broadcast(text);
            }
        });
    }
}
