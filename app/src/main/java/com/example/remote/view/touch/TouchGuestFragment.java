package com.example.remote.view.touch;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.remote.GuestAdapter;
import com.example.remote.GuestFactory;
import com.example.remote.event.MsgEvent;
import com.example.remote.event.TouchEvent;

public class TouchGuestFragment extends TouchFragment implements GuestAdapter {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        GuestFactory.peerHost(getContext(), this, this);
        GuestFactory.peerHost2(getContext(), this, this);
    }


    @Override
    public void onTouch(TouchEvent event) {
        long time = SystemClock.uptimeMillis();

        int x = (int) (event.x * view.getWidth());
        int y = (int) (event.y * view.getHeight());

        MotionEvent motionEvent = MotionEvent.obtain(time, time, event.action, x, y, 0);
        view.dispatchTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    @Override
    public void onMsg(MsgEvent event) {

    }


}
