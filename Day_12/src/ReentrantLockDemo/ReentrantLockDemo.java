package com.company.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockDemo
 * @Description ReentrantLock使用Demo示例(默认公平锁)
 * @Author Lijuce_K
 * @Date 2021/8/10 0010 21:39
 * @Version 1.0
 **/
public class ReentrantLockDemo {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(ReentrantLockDemo::test, "线程A").start();
        new Thread(ReentrantLockDemo::test, "线程B").start();
        // 或者也可以这种lambda表达式方式实现
        new Thread(() -> test(), "线程A").start();
        new Thread(() -> test(), "线程B").start();
    }

    public static void test() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获取了锁");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            lock.unlock();
        }

    }
}
