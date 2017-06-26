package com.app.frame.concurrent.container;

import com.app.frame.util.VLog;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者-消费者模型
 */

public class Container<T> {

    public static final String TAG = "Producer-Consumer";

    private BlockingQueue<T> set;

    //最大商品数量
    private volatile int maxProductSize;

    //已生产数量
    private volatile int productedCount;

    //已消费数量
    private volatile int consumedCount;

    private volatile boolean stop;

    /**
     * @param maxContainerSize 最大库存数量
     * @param maxProductSize   最大产品数量
     */
    public Container(int maxContainerSize, int maxProductSize) {
        set = new ArrayBlockingQueue<>(maxContainerSize);
        this.maxProductSize = maxProductSize;
    }

    public Container(int containnerSize) {
        set = new ArrayBlockingQueue<>(containnerSize);
        this.maxProductSize = -1;
    }

    public BlockingQueue<T> getSet() {
        return set;
    }

    public void stop() {
        stop = true;
    }

    public boolean needStop(boolean isPruducer) {
        if (stop) {
            return true;
        }
        int count = isPruducer ? productedCount : consumedCount;
        if (maxProductSize > 0 && count >= maxProductSize) {
            return true;
        }
        return false;
    }

    public boolean isStop() {
        return stop;
    }

    private void put(T t) {
        try {
            set.put(t);
            productedCount++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private T get() {
        T t = null;
        try {
            t = set.take();
            consumedCount++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static class Producer<T> implements Runnable {

        private Container<T> container;

        public Producer(Container<T> container) {
            this.container = container;
        }

        @Override
        public final void run() {
            while (true) {
                if (container.needStop(true)) {
                    VLog.i(TAG, "Producer stop !");
                    return;
                }
                T t = produce(container.productedCount);
                container.put(t);
            }
        }

        public T produce(int position) {
            return null;
        }

        public void stop() {
            container.stop = true;
        }

    }

    public static class Consumer<T> implements Runnable {

        private Container<T> container;


        public Consumer(Container<T> container) {
            this.container = container;
        }

        @Override
        public final void run() {
            while (true) {
                if (container.needStop(false)) {
                    stop();
                    VLog.i(TAG, "Consumer stop !");
                    return;
                }
                int position = container.consumedCount;
                T t = container.get();
                consume(position, t);
            }
        }

        public void consume(int position, T t) {
        }

        public void stop() {
            container.stop = true;
        }

    }

    /**
     * 使用实例：
     */
    public static void test() {
        //容器
        final Container<String> container = new Container<>(3, 10);
        //生产者
        Producer<String> producer = new Producer<String>(container) {

            private Random random = new Random();

            @Override
            public String produce(int position) {
                int delay = random.nextInt(2000);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String t = "" + System.currentTimeMillis();
                VLog.i(TAG, "produce[" + position + "]:" + t);
                return t;
            }
        };
        //消费者
        Consumer<String> consumer = new Consumer<String>(container) {

            private Random random = new Random();

            @Override
            public void consume(int position, String t) {
                VLog.i(TAG, "consume[" + position + "]:" + t);
                int delay = random.nextInt(2000);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //生产&消费
        new Thread(producer).start();
        new Thread(consumer).start();
    }

}