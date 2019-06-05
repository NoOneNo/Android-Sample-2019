package com.example.ws;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

// https://github.com/TooTallNate/Java-WebSocket/wiki#server-example
public class WSServerImpl extends WebSocketServer {
    private String TAG = getClass().getSimpleName();

    public WSServerImpl(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "server started successfully");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Log.i(TAG, "new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Log.i(TAG, "closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Log.i(TAG, "received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        Log.i(TAG, "received ByteBuffer from "	+ conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.i(TAG, "an error occured on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
    }
}
