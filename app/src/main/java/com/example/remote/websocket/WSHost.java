package com.example.remote.websocket;

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

public class WSHost implements Host {

    private Handler mHandler;
    private WebSocketServer mServer;
    private int port = 8887;
    private Console mConsole;

    public WSHost(Console console) {
        mConsole = console;
        HandlerThread thread = new HandlerThread("WSHost");
        thread.start();
        mHandler = new Handler(thread.getLooper());
    }

    @Override
    public void start(final Context context) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mServer = new WSServer(new InetSocketAddress(getIP(context), getPort())) {
                    @Override
                    public void onOpen(WebSocket conn, ClientHandshake handshake) {
                        super.onOpen(conn, handshake);
                        log("new connection to " + conn.getRemoteSocketAddress());
                        log("new connection: " + handshake.getResourceDescriptor());
                    }
                };
                mServer.start();

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
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mServer == null) {
                    return;
                }

                try {
                    mServer.stop();
                    mServer = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void send(String msg) {

    }

    private void broadcast(final String text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mServer.broadcast(text);
            }
        });
    }

    @Override
    public void touch(TouchEvent event) {
        broadcast(event.toString());
    }

    private void log(String text) {
        mConsole.log(text);
    }
}
