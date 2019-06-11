/*
 * Copyright (C) 2019, Xiaomi Inc. All rights reserved.
 */

package com.example.feature.loading;

public abstract class ProgressUpdater {

    private boolean mRunning = false;
    private ProgressListener mListener;

    private int mProgress = 0;
    private int mMaxProgress = 100;

    public int getProgress() {
        return mProgress;
    }

    void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    int getMaxProgress() {
        return mMaxProgress;
    }

    void setProgressListener(ProgressListener listener) {
        mListener = listener;
    }

    interface ProgressListener {
        void update(int progress);
    }

    public void start() {
        if (mRunning) {
            return;
        }
        mRunning = true;
        mProgress = 0;
    }

    public void stop() {
        if (!mRunning) {
            return;
        }
        mRunning = false;
    }

    boolean isRunning() {
        return mRunning;
    }

    void update(int progress) {
        mProgress = progress;
        if (mProgress >= mMaxProgress) {
            stop();
        }
        if (mListener != null) {
            mListener.update(mProgress);
        }
    }
}
