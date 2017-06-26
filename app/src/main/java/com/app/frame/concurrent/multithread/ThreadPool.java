package com.app.frame.concurrent.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */

public class ThreadPool {

    public ExecutorService mPool;

    public ThreadPool(int count) {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        if (corePoolSize < 2) {
            corePoolSize = 2;
        }
        int size = count < corePoolSize ? count : corePoolSize;
        mPool = new ThreadPoolExecutor(size, corePoolSize, 20L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

}
