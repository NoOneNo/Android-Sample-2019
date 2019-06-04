package com.example.ws;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.nio.ByteBuffer;

public interface WSServer {
    void onOpen(WebSocket conn, ClientHandshake handshake);
    void onClose(WebSocket conn, int code, String reason, boolean remote);
    void onMessage(WebSocket conn, String message);
    void onMessage(WebSocket conn, ByteBuffer message);
    void onError(WebSocket conn, Exception ex);
    void onStart();
}
