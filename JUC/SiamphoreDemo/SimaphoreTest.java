package com.thread;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SimaphoreTest
 * @Description 信号量Simaphore使用案例Demo
 * @Author Lijuce_K
 * @Date 2022/2/21 18:44
 * @Version 1.0
 **/
public class SimaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 7; i++) {
            new Thread(new Worker(i, semaphore)).start();
        }
    }

    public static class Worker implements Runnable {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // 请求获取信号量
                semaphore.acquire();
                System.out.println("工人" + this.num + "开始占用一台机器工作...");
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("工人" + this.num + "工作完毕，离开机器！");
                // 释放信号量
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
