package com.example.ws;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.example.utils.NetworkUtils;
import com.example.ws.event.TouchEvent;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WSControllerWrapper extends HandlerThread implements WSController{

    private Handler handler;
    private WebSocketServer server;

    WSControllerWrapper(String name) {
        super(name);
    }

    @Override
    public synchronized void start() {
        super.start();
        handler = new Handler(getLooper());
    }

    void startServer(final Context context) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                server = new WSServerImpl(new InetSocketAddress(getIP(context), 8887));
                server.start();
            }
        });
    }

    private String getIP(Context context) {
        return NetworkUtils.getIP(context);
    }

    void stopServer() {

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
}
