package com.company.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CustomThreadFactory
 * @Description 自定义线程池
 * 改造拒绝策略，当线程池满时加入队列会进行阻塞
 * @Author Lijuce_K
 * @Date 2021/8/28 0028 10:36
 * @Version 1.0
 **/
public class CustomThreadPoolExector {

    private ThreadPoolExecutor pool = null;

    /**
     * 线程池参数初始化
     */
    public void init() {
        pool = new ThreadPoolExecutor(
                5,
                10,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }

    /**
     * 关闭线程池
     */
    public void close() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }
    /**
     * 批量创建线程的工厂
     */
    private class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            String threadName = CustomThreadPoolExector.class.getSimpleName() + count.addAndGet(1);
            System.out.println(threadName);
            thread.setName(threadName);
            return thread;
        }
    }

    /**
     * 自定义拒绝策略
     */
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//            System.out.println("error.............");
            try {
                // 若队列剩余空间不足，会进行阻塞，直到队列有空余
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ExecutorService getCustomThreadPoolExecutor() {
        return this.pool;
    }

    public static void main(String[] args) {
        CustomThreadPoolExector threadPool = new CustomThreadPoolExector();
        // 初始化
        threadPool.init();

        ExecutorService pool = threadPool.getCustomThreadPoolExecutor();
        for (int i = 0; i < 100; i++) {
            System.out.println("summit the " + i + " task!!!");
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("running--------------------");
                }
            });
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
