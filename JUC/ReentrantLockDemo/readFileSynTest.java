package com.company.lock;

/**
 * @ClassName readFileJUCTest
 * @Description synchronized锁实现并发读文件
 * @Author Lijuce_K
 * @Date 2021/8/30 0030 18:09
 * @Version 1.0
 **/
public class readFileSynTest {
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

    public synchronized static void get(Thread thread) {
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
    }


}
