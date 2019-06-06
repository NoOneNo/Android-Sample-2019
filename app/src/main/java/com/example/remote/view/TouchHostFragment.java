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
import com.example.remote.Console;
import com.example.remote.Host;
import com.example.remote.HostFactory;
import com.example.remote.event.TouchEvent;

public class TouchHostFragment extends TouchFragment {

    private Host wsControllerWrapper;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wsControllerWrapper = HostFactory.createHost2(getContext(), this);
        wsControllerWrapper.start(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wsControllerWrapper.close();
    }

    @Override
    public void onTouchEvent(TouchEvent event) {
        wsControllerWrapper.touch(event);
    }
}
