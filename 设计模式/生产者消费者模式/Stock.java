package com.company.thread;

/**
 * @ClassName Stock
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/8/7 0007 21:16
 * @Version 1.0
 **/
public class Stock {
    private boolean hasComputer = false;
    private String name;
    private int computerNums = 1000;

    public static void main(String[] args) {
        Stock stock = new Stock();
        // 定义生产者
        Thread p1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    stock.produceOneComputer("华为");
                }
            }
        });
        Thread p2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    stock.produceOneComputer("戴尔");
                }
            }
        });

        // 定义消费者
        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    stock.consumeOneComputer("华为");
                }
            }
        });
        Thread s2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    stock.consumeOneComputer("戴尔");
                }
            }
        });

        p1.start();
        p2.start();
        s1.start();
        s2.start();
    }

    public synchronized void produceOneComputer(String name) {
        while (hasComputer) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name;
        System.out.println("生产者：生产了" + name);
        this.hasComputer = true;
        // 告诉消费者已经可以开始消费了
        this.notifyAll();
    }

    public synchronized void consumeOneComputer(String name) {
        while (!hasComputer) {
            // 没有产品进行消费时，等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("消费者：消费了" + name);
        this.hasComputer = false;
        // 告诉生产者需要开始生产了
        this.notifyAll();
    }
}
