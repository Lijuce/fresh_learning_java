package com.company.thread;

import java.util.concurrent.*;

/**
 * Callable接口，Future类的使用示例
 */
public class CallableFutureDemo implements Callable<Integer> {
    // 继承Callable并实现call接口
    @Override
    public Integer call(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2,2,1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        CallableFutureDemo task = new CallableFutureDemo();
        Future<Integer> submitResult = null;
        for (int i = 0; i < 5; i++) {
            submitResult = poolExecutor.submit(new CallableFutureDemo());  // 线程池提交该任务task
            System.out.println(submitResult.get());   // 通过Future的get方法获取返回值
        }
    }
}
