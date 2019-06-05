package com.example.remote;

import com.example.remote.event.TouchEvent;

public interface Controller {
    void touch(TouchEvent event);
    void stick(int index, int percent);
}
