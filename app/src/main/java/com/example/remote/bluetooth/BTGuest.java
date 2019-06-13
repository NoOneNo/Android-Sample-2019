package com.example.remote.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.remote.AbstractGuest;
import com.example.remote.Console;
import com.example.remote.Guest;
import com.example.remote.GuestAdapter;
import com.example.remote.event.Event;
import com.example.remote.event.EventParser;
import com.example.remote.event.MsgEvent;
import com.example.remote.event.TouchEvent;

public class BTGuest extends AbstractGuest {

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
                    log("" + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    Log.i("debug", "b:"+msg.arg1);
                    String message = new String(readBuf, 0, msg.arg1);
                    onMsg(message);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    log( "Connected to " + msg.getData().getString(Constants.DEVICE_NAME));
                    break;
                case Constants.MESSAGE_TOAST:
                    log(msg.getData().getString(Constants.TOAST));
                    break;
            }
        }
    }

    private BluetoothChatService service;

    public BTGuest(Context context, Console console) {
        Handler handler = new MyHandler();
        service = new BluetoothChatService(context, handler);
        start(context);
    }

    public void start(Context context) {
        service.start();
    }

    public void close() {
        service.stop();
    }

    public void connect(BluetoothDevice device) {
        service.connect(device, true);
    }
}
