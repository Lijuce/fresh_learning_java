package com.company.syn;

import java.util.ArrayList;
import java.util.List;

/**
 * 不安全案例之线程不安全
 */
public class UnsafeList {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                list.add(Thread.currentThread().getName());
            }).start();
        }
        System.out.println(list.size());
    }
}
