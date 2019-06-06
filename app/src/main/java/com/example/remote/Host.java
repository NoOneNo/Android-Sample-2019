package com.example.remote;

import android.content.Context;
import com.example.remote.event.TouchEvent;

public interface Host {
    void start(Context context);
    void touch(TouchEvent event);
    void close();
}
