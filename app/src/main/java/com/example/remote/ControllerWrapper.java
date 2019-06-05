package com.example.remote;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.example.utils.NetworkUtils;
import com.example.remote.base.WSServer;
import com.example.remote.event.TouchEvent;
import com.example.remote.view.WSControlFragment;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ControllerWrapper extends HandlerThread implements Controller {

    private Handler handler;
    private WebSocketServer server;

    private int port = 8887;

    private WSControlFragment fragment;

    public ControllerWrapper(String name, WSControlFragment fragment) {
        super(name);
        this.fragment = fragment;
    }

    @Override
    public synchronized void start() {
        super.start();
        handler = new Handler(getLooper());
    }

    public void startServer(final Context context) {
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
            }
        });
    }

    public String getIP(Context context) {
        return NetworkUtils.getIP(context);
    }

    public int getPort() {
        return port;
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

    @Override
    public void stick(int index, int percent) {

    }

    void log(String text) {
        fragment.log(text);
    }
}
