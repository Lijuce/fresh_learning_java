package com.company.synUnsafeCase;

import java.util.ArrayList;

/**
 * 不安全案例之线程不安全
 */
public class UnsafeListSyn {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                // 同步代码块形式解决并发问题
                synchronized (list){
                    list.add(Thread.currentThread().getName());
                }
            }).start();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
