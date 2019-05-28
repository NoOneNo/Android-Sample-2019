package com.example.ws;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.format.Formatter;

import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static android.content.Context.WIFI_SERVICE;

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

    public void startServer(final Context context) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                server = new WSServer(new InetSocketAddress(getIP(context), 8887));
                server.start();
            }
        });
    }

    private String getIP(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
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
