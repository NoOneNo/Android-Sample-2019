package com.example.remote.bluetooth;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.remote.AbstractHost;
import com.example.remote.Console;

public class BTHost extends AbstractHost {

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            log("" + "STATE_CONNECTED");
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            log("" + "STATE_CONNECTING");
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            log("" + "NOT CONNECTED");
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:

                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    log( "Connected to " + msg.getData().getString(Constants.DEVICE_NAME));
                    break;
                case Constants.MESSAGE_TOAST:
                    log(msg.getData().getString(Constants.TOAST));
                    break;
            }
        }
    };

    private BluetoothChatService service;
    private Console console;

    public BTHost(Context context, Console console) {
        service = new BluetoothChatService(context, new MyHandler());
        this.console = console;
    }

    @Override
    public void start(Context context) {
        service.start();
    }

    @Override
    public void close() {
        service.stop();
    }

    @Override
    protected void send(String message) {
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            service.write(send);
        }
    }

    private void log(String msg) {
        console.log(msg);
    }
}
