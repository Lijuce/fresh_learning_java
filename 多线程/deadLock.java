package com.company.thread;

/**
 * @ClassName deadLock
 * @Description 死锁实现示例
 * @Author Lijuce_K
 * @Date 2021/8/22 0022 16:26
 * @Version 1.0
 **/
public class deadLock {
    public static void main(String[] args) {
        Program p1 = new Program();
        Program p2 = new Program();
        p1.flag = 1;
        p2.flag = 2;
        new Thread(p1).start();
        new Thread(p2).start();
    }
}

class Program implements Runnable {
    public static Object obj1 = new Object();
    public static Object obj2 = new Object();

    // 标志位
    public int flag = 1;

    @Override
    public void run() {
        if (flag == 1) {
            synchronized (obj1) {
                System.out.println("flag: " + flag + "锁住了资源obj1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("flag: " + flag + ", 等待获取资源obj2");

                synchronized (obj2) {
                    System.out.println("flag: " + flag + ", 获得资源obj2");
                }
            }
        } else if(flag == 2) {
            synchronized (obj2) {
                System.out.println("flag: " + flag + ", 锁住了资源obj2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("flag: " + flag + ", 等待获取资源obj1");
                synchronized (obj1) {
                    System.out.println("flag: " + flag + ", 获得资源obj1");
                }
            }
        }
    }
}