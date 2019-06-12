package com.example.remote;

import com.example.remote.event.MsgEvent;
import com.example.remote.event.TouchEvent;

public interface GuestAdapter {
    void onTouch(TouchEvent event);
    void onMsg(MsgEvent event);
}
