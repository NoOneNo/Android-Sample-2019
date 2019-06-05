package com.example.remote.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.R;
import com.example.remote.ControllerWrapper;
import com.example.remote.event.TouchEvent;

public class WSControlFragment extends Fragment implements View.OnTouchListener {

    private ControllerWrapper wsControllerWrapper = new ControllerWrapper("ControllerWrapper", this);

    private View btn;
    private TextView logTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!wsControllerWrapper.isAlive()) {
            wsControllerWrapper.start();
        }
        wsControllerWrapper.startServer(getContext());

        View view = inflater.inflate(R.layout.frag_ws, container, false);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.float_button);
        logTV = view.findViewById(R.id.log_tv);

        log("ip:" + wsControllerWrapper.getIP(getContext()));
        log("port:" + wsControllerWrapper.getPort());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        wsControllerWrapper.stopServer();
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

        wsControllerWrapper.touch(new TouchEvent(event.getAction(), horizontalBias, verticalBias));

        return true;
    }

    public void log(String text) {
        logTV.append("\n" + text);
    }
}
