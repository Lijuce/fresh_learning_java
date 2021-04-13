package com.company;
//  2021年4月12日

public class TestThread01 extends Thread{
    // 重写run方法
    @Override
    public void run(){
        for (int i = 0; i < 200; i++){
            System.out.println("我在看代码————————————————"+i);
        }
    }

    public static void main(String[] args) {
        TestThread01 testThread01 = new TestThread01();
        testThread01.start();  // 调用start开启进程

        for (int i = 0; i < 200; i++){
            System.out.println("我在学习————————————————" + i);
        }

    }


}
