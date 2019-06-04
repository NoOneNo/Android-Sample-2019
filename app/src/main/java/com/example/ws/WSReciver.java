package com.example.ws;

import com.example.ws.event.TouchEvent;

public interface WSReciver {
    void onTouch(TouchEvent event);
    void onStick(int index, int percent);
}
