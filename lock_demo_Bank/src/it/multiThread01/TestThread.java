package it.multiThread01;

/**
 * 多线程编码：继承Thread
 * 然后重写run方法
 */
public class TestThread extends Thread{
    // 需重写run方法
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("我在看代码。。。。。。。。。。。");
        }
    }

    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        testThread.start();
        for (int i = 0; i < 200; i++) {
            System.out.println("我在看视频。。。。。。。。。。。");
        }
    }
}
