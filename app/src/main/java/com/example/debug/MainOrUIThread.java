package com.example.debug;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.example.R;

public class MainOrUIThread {

    HandlerThread thread = new HandlerThread("hello");
    Handler mHandler = new Handler();
    Handler threadhandler;

    public void start(final Activity context) {
        thread.start();

//        threadhandler = new Handler(thread.getLooper());
        threadhandler = new Handler();

        threadhandler.post(new Runnable() {
            @Override
            public void run() {
                addWindow(context);
            }
        });

        addWindow2(context);

//        delayUI();
    }

    private void addWindow(Activity context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        lp.format = PixelFormat.TRANSLUCENT;

        final View view = context.getLayoutInflater().inflate(R.layout.view_text, null);
        final View btn = view.findViewById(R.id.title);

        windowManager.addView(view, lp);

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(900);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        btn.startAnimation(rotate);
    }

    private void addWindow2(Activity context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        lp.format = PixelFormat.TRANSLUCENT;

        final View view = context.getLayoutInflater().inflate(R.layout.view_text, null);
        final View btn = view.findViewById(R.id.title);

        windowManager.addView(view, lp);

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(300);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        btn.startAnimation(rotate);
    }


    private void delayUI() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                delayUI();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
