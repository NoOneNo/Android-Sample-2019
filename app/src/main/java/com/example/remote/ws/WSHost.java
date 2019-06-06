package com.example.remote.ws;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.example.remote.Console;
import com.example.remote.Host;
import com.example.remote.event.TouchEvent;
import com.example.utils.NetworkUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WSHost extends HandlerThread implements Host {

    private Handler handler;
    private WebSocketServer server;

    private int port = 8887;

    private Console console;

    public WSHost(String name, Console console) {
        super(name);
        this.console = console;
    }

    @Override
    public void start(final Context context) {
        start();
        handler = new Handler(getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                server = new WSServer(new InetSocketAddress(getIP(context), port)) {
                    @Override
                    public void onOpen(WebSocket conn, ClientHandshake handshake) {
                        super.onOpen(conn, handshake);
                        log("new connection to " + conn.getRemoteSocketAddress());
                        log("new connection: " + handshake.getResourceDescriptor());
                    }
                };
                server.start();

                log("ip:" + getIP(context));
                log("port:" + getPort());
            }
        });
    }

    private String getIP(Context context) {
        return NetworkUtils.getIP(context);
    }

    private int getPort() {
        return port;
    }

    public void close() {
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

    private void broadcast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                server.broadcast(text);
            }
        });
    }

    @Override
    public void touch(TouchEvent event) {
        broadcast(event.toString());
    }

    private void log(String text) {
        console.log(text);
    }
}
