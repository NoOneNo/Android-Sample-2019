package com.example.remote;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.example.R;
import com.example.remote.bluetooth.BTGuest;
import com.example.remote.websocket.WSGuest;
import com.example.utils.NetworkUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class GuestFactory {

    @SuppressLint("SetTextI18n")
    public static void peerHost2(Context context, final GuestAdapter adapter, final Console console) {
        BTGuest guest = new BTGuest(context, console);
        guest.setAdapter(adapter);
        BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("E0:DC:FF:CA:43:EC");
        guest.connect(device);
    }

    @SuppressLint("SetTextI18n")
    public static void peerHost(Context context, final GuestAdapter guest, final Console console) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setView(R.layout.dialog_ws_config)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText ip = ((AlertDialog)dialog).findViewById(R.id.ip_config);
                    EditText port = ((AlertDialog)dialog).findViewById(R.id.port_config);
                    String ipConfig = ip.getText().toString();
                    String portConfig = port.getText().toString();

                    GuestFactory.peerHost(ipConfig, Integer.valueOf(portConfig), guest, console);
                }
            }).create();

        alertDialog.show();

        EditText ip = alertDialog.findViewById(R.id.ip_config);
        EditText port = alertDialog.findViewById(R.id.port_config);
        ip.setText(NetworkUtils.getIP(context).substring(0, 7));
        port.setText("8887");
    }

    private static void peerHost(String ip, int port, GuestAdapter guest, Console console) {
        WSGuest client;
        try {
            client = new WSGuest();
            client.setAdapter(guest);
            client.setConsole(console);
            client.connect(new URI("ws://" + ip + ":" + port));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
