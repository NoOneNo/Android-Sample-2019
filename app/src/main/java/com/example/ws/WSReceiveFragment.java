package com.example.ws;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.example.R;
import com.example.utils.NetworkUtils;
import com.example.ws.event.TouchEvent;

public class WSReceiveFragment extends Fragment implements WSReciver, View.OnTouchListener {
    private View btn;
    private TextView logTV;

    EditText ip;
    EditText port;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_ws, container, false);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.float_button);
        logTV = view.findViewById(R.id.log_tv);

        // duplicate clounm
        // update twice
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(R.layout.dialog_ws_config)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ipConfig = ip.getText().toString();
                String portConfig = port.getText().toString();

                boolean result = WSControllerSearch.peerController(ipConfig, Integer.valueOf(portConfig), WSReceiveFragment.this);
                if (result) {
                    log("connect success");
                    dialog.dismiss();
                } else {
                    log("connect failed");
                }
            }
        }).create();

        alertDialog.show();

        ip = alertDialog.findViewById(R.id.ip_config);
        ip.setText(NetworkUtils.getIP(getContext()).substring(0, 7));
        port = alertDialog.findViewById(R.id.port_config);
        port.setText("8887");
    }


    @Override
    public void onTouch(final TouchEvent event) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long time = SystemClock.uptimeMillis();

                int x = (int) (event.x * view.getWidth());
                int y = (int) (event.y * view.getHeight());

                MotionEvent motionEvent = MotionEvent.obtain(time, time, event.action, x, y, 0);
                view.dispatchTouchEvent(motionEvent);
                motionEvent.recycle();
            }
        });
    }

    @Override
    public void onStick(int index, int percent) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float horizontalBias = event.getX() / v.getWidth();
        float verticalBias = event.getY() / v.getHeight();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            horizontalBias = 0.5f;
            verticalBias = 0.5f;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.horizontalBias = horizontalBias;
        layoutParams.verticalBias = verticalBias;
        btn.setLayoutParams(layoutParams);

        return true;
    }

    void log(String text) {
        logTV.append("\n" + text);
    }


}
