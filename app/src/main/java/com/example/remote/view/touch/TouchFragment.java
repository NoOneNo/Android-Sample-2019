package com.example.remote.view.touch;

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
import com.example.remote.Console;
import com.example.remote.event.TouchEvent;

public class TouchFragment extends Fragment implements View.OnTouchListener, Console {

    private View btn;
    private TextView logTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ws, container, false);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.float_button);
        logTV = view.findViewById(R.id.log_tv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

        onTouchEvent(new TouchEvent(event.getAction(), horizontalBias, verticalBias));

        return true;
    }

    public void onTouchEvent(TouchEvent event) {

    }

    public void log(String text) {
        logTV.append("\n" + text);
    }
}
