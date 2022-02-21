package com.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName CyclicBarrier
 * @Description CyclicBarrier类使用案例
 * @Author Lijuce_K
 * @Date 2022/2/21 17:19
 * @Version 1.0
 **/
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            new Thread(new Writer(cyclicBarrier)).start();
        }
    }

    public static class Writer implements Runnable {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cy) {
            this.cyclicBarrier = cy;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据");

            try {
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完成！！");

                // 每完成一次任务，执行一次，阻塞等待
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程完成任务。" + Thread.currentThread().getName() + "继续执行其他任务...");

        }
    }
}
