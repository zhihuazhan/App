package com.app.frame.concurrent.multithread;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 并发任务模型：设置任务优先级,按批次执行
 * //
 * //
 * //                               run()
 * //
 * //               A1   A2    A3    A4    A5    A6    A7    A8
 * //
 * //                           B1    B2    B3
 * //
 * //                  C1    C2    C3    C4    C5    C6
 * //
 * //                             D1    D2
 * //
 * //                                E1
 */

public class Concurrent {

    public ThreadPool mThreadPool;

    public ConcurrentData mConcurrentData;

    public List<List<Action>> mActionList;

    public List<CountDownLatch> mLatchList;

    public List<Action> mList;

    public boolean mStarted;

    public Concurrent(List<Action> list) {
        mList = list;
    }

    public void init() {
        List<Action> list = mList;
        mList = null;
        int count = list == null ? 0 : list.size();
        if (count == 0) {
            return;
        }
        Collections.sort(list);

        int maxThreadCount = 0;
        boolean needThreadPool = false;

        //mActionList
        mActionList = new ArrayList<>(count);
        int priority = Integer.MIN_VALUE;
        for (Action a : list) {
            if (priority != a.priority) {
                mActionList.add(new ArrayList<Action>());
            }
            int index = mActionList.size() - 1;
            List<Action> actions = mActionList.get(index);
            actions.add(a);

            if (!needThreadPool && (a.handler == null)) {
                needThreadPool = true;
            }
        }

        //mLatchList
        //maxThreadCount
        mLatchList = new ArrayList<>();
        for (List<Action> l : mActionList) {
            int size = l.size();
            if (size == 1) {
                mLatchList.add(null);
            } else {
                CountDownLatch latch = new CountDownLatch(size);
                mLatchList.add(latch);
                for (Action a : l) {
                    a.setLatch(latch);
                }
            }
            if (size > maxThreadCount) {
                maxThreadCount = size;
            }
        }

        //needThreadPool
        if (needThreadPool) {
            mThreadPool = new ThreadPool(maxThreadCount);
        }
    }

    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Concurrent.this.run();
            }

        }).start();
    }

    private void run() {
        if (mStarted) {
            return;
        }
        mStarted = true;
        init();

        int size = mActionList.size();
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            List<Action> list = mActionList.get(i);
            for (Action a : list) {
                if (a.handler == null) {
                    mThreadPool.mPool.execute(a);
                } else {
                    a.handler.post(a);
                }
            }
            CountDownLatch latch = mLatchList.get(i);
            if (latch != null) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (mThreadPool != null) {
            mThreadPool.mPool.shutdown();
        }
    }


}
