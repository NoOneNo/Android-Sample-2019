package com.example.remote;

import com.example.remote.event.TouchEvent;

public interface WSReciver {
    void onTouch(TouchEvent event);
    void onStick(int index, int percent);
}
