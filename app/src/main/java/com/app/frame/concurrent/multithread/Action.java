package com.app.frame.concurrent.multithread;

import android.os.Handler;

import java.util.concurrent.CountDownLatch;

/**
 * 任务
 */

public abstract class Action implements Runnable, Comparable<Action> {

    public int priority;

    public Handler handler;

    public CountDownLatch latch;

    public abstract void act();

    @Override
    public void run() {
        act();
        if (latch != null) {
            latch.countDown();
        }
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public int compareTo(Action that) {
        return priority - that.priority;
    }

}