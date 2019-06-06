package com.example.remote;

import com.example.remote.event.TouchEvent;

public interface WSReceiver {
    void onTouch(TouchEvent event);
    void onStick(int index, int percent);
}
