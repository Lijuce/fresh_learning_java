package com.company.thread;

import java.util.concurrent.*;

/**
 * Callable和FutureTask类的使用示例
 */
public class CallableFutureTaskDemo implements Callable<Integer> {
    private static int count = 0;
    @Override
    public Integer call(){
        count++;
        try {
            // 模拟延迟返回处理结果
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(new CallableFutureTaskDemo());
            pool.submit(futureTask);// 此时无返回值
            System.out.println(futureTask.get());
        }
    }
}
