package com.example.remote;

import com.example.remote.event.MsgEvent;
import com.example.remote.event.TouchEvent;

public interface Guest {
    void onTouch(TouchEvent event);
    void onMsg(MsgEvent msg);
}
