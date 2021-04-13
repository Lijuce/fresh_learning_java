package com.company;
// 2021年4月12日
// 线程停止方法
// 1. 建议线程正常停止---》利用次数，不建议死循环
// 2. 建议使用标志位---> 设置一个标志位
// 3. 不使jdk不建议的方法

public class TestStopThread implements Runnable{

    // 1. 设置一个标志位
    private boolean flag = true;

    @Override
    public void run(){
        int i = 0;
        while (flag){
            System.out.println("run...Thread" + i++);
        }
    }

    // 2. 设置公开方法停止线程，转换标志位
    public void stop(){
        this.flag = false;
    }

    public static void main(String[] args) {
        TestStopThread stopThread = new TestStopThread();
        new Thread(stopThread).start();

        for (int i = 0; i < 1000; i++){
            System.out.println("main" + i);
            if (i==900){
                stopThread.stop();
                System.out.println("已停止线程");
            }
        }
    }
}
