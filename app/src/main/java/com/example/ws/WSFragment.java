package com.example.ws;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

import com.example.R;

public class WSFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private static WSThread wsThread = new WSThread("WSThread");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!wsThread.isAlive()) {
            wsThread.start();
        }
        wsThread.startServer();

        return inflater.inflate(R.layout.frag_ws, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatSeekBar seekBar = view.findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        wsThread.stopServer();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        wsThread.broadcast("{\"progress\":" + progress +"}");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
