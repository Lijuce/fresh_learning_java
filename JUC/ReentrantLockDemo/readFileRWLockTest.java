package com.company.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName readFileRWLockTest
 * @Description ReentrantReadWriteLock读锁实现并发读文件-利用其读锁共享机制
 * @Author Lijuce_K
 * @Date 2021/8/30 0030 18:17
 * @Version 1.0
 **/
public class readFileRWLockTest {
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                get(Thread.currentThread());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                get(Thread.currentThread());
            }
        }).start();
    }

    public static void get(Thread thread) {
        lock.readLock().lock();
        System.out.println("Start time: " + System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            // 模拟读取文件时延
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(thread.getName() + "正在读取文件......");
        }
        System.out.println(thread.getName() + "读取文件完毕!!!");
        System.out.println("End time: " + System.currentTimeMillis());
        lock.readLock().unlock();
    }
}
