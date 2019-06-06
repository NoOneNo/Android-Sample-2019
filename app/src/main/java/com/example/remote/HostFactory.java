package com.example.remote;

import android.content.Context;
import com.example.remote.bluetooth.BTHost;
import com.example.remote.websocket.WSHost;

public class HostFactory {
    public static Host createHost(Console console) {
        return new WSHost("WSHost", console);
    }

    public static Host createHost2(Context context, Console console) {
        return new BTHost(context, console);
    }
}
