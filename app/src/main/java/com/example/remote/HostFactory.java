package com.example.remote;

import com.example.remote.ws.WSHost;

public class HostFactory {
    public static Host createHost(Console console) {
        return new WSHost("WSHost", console);
    }
}
