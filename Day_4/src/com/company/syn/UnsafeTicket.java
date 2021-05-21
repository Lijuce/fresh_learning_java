package com.company.syn;

import java.util.ArrayList;
import java.util.List;

/**
 * 不安全案例之抢票
 */
public class UnsafeTicket {
    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();
        new Thread(buyTicket, "小红").start();
        new Thread(buyTicket,"小明").start();
        new Thread(buyTicket,"黄牛").start();
    }

}

class BuyTicket implements Runnable{
    private int ticketNum = 10;  // 票余数
    boolean flag = true;  // 买票状态

    @Override
    public void run(){
        while (flag){
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void buy() throws InterruptedException {
        // 判断是否有余票，无则直接返回
        if(ticketNum <= 0){
            flag = false;
            return;
        }

        // 模拟购票延时
        Thread.sleep(100);

        // 有余票，开始买票
        System.out.println(Thread.currentThread().getName() + "买到票" + ticketNum--);
    }
}


