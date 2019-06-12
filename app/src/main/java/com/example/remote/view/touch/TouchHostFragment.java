package com.example.remote.view.touch;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.remote.Host;
import com.example.remote.HostFactory;
import com.example.remote.event.TouchEvent;

public class TouchHostFragment extends TouchFragment {

    private Host host;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        host = HostFactory.createHost2(getContext(), this);
        host.start(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        host.close();
    }

    @Override
    public void onTouchEvent(TouchEvent event) {
        host.touch(event);
    }
}
