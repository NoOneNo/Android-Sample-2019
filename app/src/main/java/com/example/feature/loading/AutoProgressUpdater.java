/*
 * Copyright (C) 2019, Xiaomi Inc. All rights reserved.
 */

package com.example.feature.loading;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class AutoProgressUpdater extends ProgressUpdater implements Runnable {

    private static long mTotalTime = 10*1000; // ms

    private long mStartTime;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ProgressStrategy mStrategy = new LinearOutSlowInStrategy();

    private long getStartTime() {
        return mStartTime;
    }

    public void setTotalTime(long totalTime) {
        mTotalTime = totalTime;
    }

    @Override
    public void start() {
        if (isRunning()) {
            return;
        }
        super.start();
        mStartTime = SystemClock.uptimeMillis();
        run();
    }

    @Override
    public void run() {
        if (!isRunning()) {
            return;
        }

        int progress = mStrategy.update(this);
        if (getProgress() < progress) {
            update(progress);
        }

        mHandler.post(this);
    }


    interface ProgressStrategy {
        int update(AutoProgressUpdater updater);
    }

    class LinearOutSlowInStrategy implements ProgressStrategy {

        LinearOutSlowInInterpolator mInterpolator = new LinearOutSlowInInterpolator();

        @Override
        public int update(AutoProgressUpdater updater) {
            long time = SystemClock.uptimeMillis() - updater.getStartTime();
            float percent = (float) time / mTotalTime;
            percent = mInterpolator.getInterpolation(percent);
            percent = mInterpolator.getInterpolation(percent);
            int progress = (int) (percent * (updater.getMaxProgress() - 1));
            return progress;
        }
    }

    class LogisticProgressStrategy implements ProgressStrategy {

        private double yStart = 0.5;
        private double xStart = 0;
        private double yEnd = 0.9999;
        private double xEnd = logisticRev(yEnd);

        private double logisticRev(double y) {
            return Math.log(y / (1 - y));
        }

        /**
         * 5000 - 0
         * 5999 - 4050
         * 9999 - 9210
         *
         * @param x 0 <= x <= 0.9210
         * @return 0.5000 <= y <= 0.9999
         */
        private double logistic(double x) {
            return 1.0 / (1 + Math.exp(-x));
        }

        private double normalizeLogistic(double percentX) {
            double x = percentX * (xEnd - xStart) + xStart;
            double y = logistic(x);
            return (y - yStart) / (yEnd - yStart);
        }

        @Override
        public int update(AutoProgressUpdater updater) {
            long time = SystemClock.uptimeMillis() - updater.getStartTime();
            double percent = (double) time / mTotalTime;
            percent = normalizeLogistic(percent);
            int progress = (int) (percent * updater.getMaxProgress());
            if (progress >= updater.getMaxProgress()) {
                progress = updater.getMaxProgress() - 1;
            }
            return progress;
        }
    }
}
