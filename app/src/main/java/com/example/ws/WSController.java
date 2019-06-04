package com.example.ws;

import com.example.ws.event.TouchEvent;

public interface WSController {
    void touch(TouchEvent event);

    void stick(int index, int percent);
}
