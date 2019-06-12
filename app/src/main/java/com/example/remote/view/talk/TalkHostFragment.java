package com.example.remote.view.talk;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.remote.Host;
import com.example.remote.HostFactory;

public class TalkHostFragment extends TalkFragment {

    private Host host;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        host = HostFactory.createHost2(getContext(), this);
        host.start(getContext());
    }

    @Override
    public void send(String msg) {

    }

}
