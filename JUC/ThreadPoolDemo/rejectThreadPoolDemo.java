package com.company.thread;

import java.util.concurrent.*;

/**
 * @ClassName rejectThreadPoolDemo
 * @Description 线程池自定义拒绝策略Demo1-打印线程信息
 * @Author Lijuce_K
 * @Date 2021/8/28 0028 9:56
 * @Version 1.0
 **/
public class rejectThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        // 打印丢弃任务
                        System.out.println(r.toString() + "is discarded!!!");
                    }
                });
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(myTask);
            Thread.sleep(10);
        }
    }
}

class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println(System.currentTimeMillis()
                + ":Thread ID:" + Thread.currentThread().getId());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
