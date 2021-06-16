package com;

import com.jedis.JedisService;

public class Case {


    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("A", 10);
        MyThread myThread2 = new MyThread("B", 20);
        myThread1.start();
        myThread2.start();
    }
}


class MyThread extends Thread{
    JedisService business;

    public MyThread(String id, long num){
        business = new JedisService(id, num);
    }
    public void run(){
        while (true){
            business.service();
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}