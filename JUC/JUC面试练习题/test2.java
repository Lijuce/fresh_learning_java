/*
有3个线程，线程1、2和3，每个线程严格执行自己的动作
流程如下：
    1. 线程1执行动作1后，创建线程2和线程3，创建线程2和线程3后，block线程1，直到线程2完成动作2，线程3完成动作3；
    2.当线程2启动后，执行动作2，执行完动作2后，block线程2，直到线程1完成动作4，然后线程2自己结束
    3.线程3启动后，执行动作3，执行完动作3后，block 线程3，直到线程1完成4，然后线程3自己结束。
    4.等动作2和动作3完成后，唤醒线程1，唤醒线程1后，执行动作4
    5.线程1执行完动作4后，等待线程2和线程3结束。
 */

/**
 * @ClassName main
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2022/2/18 22:44
 * @Version 1.0
 **/
public class main {
    public static class ThreadPrinter implements Runnable {
        private String name;
        private Object prev;
        private Object self;

        private ThreadPrinter(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                // 先获取 prev 锁
                synchronized (prev) {
                    // 再获取 self 锁
                    synchronized (self) {
                        System.out.println(name);
                        count--;

                        // 唤醒其它线程竞争 self 锁，注意此时 self 锁并未立即释放
                        self.notifyAll();
                    }

                    try {
                        if (count == 0) {
                            prev.notifyAll();
                        } else {
                            // 立即释放 prev 锁，当前线程休眠，等待唤醒
                            prev.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static Object mainLock = new Object();

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch cd = new CountDownLatch(1);

        Thread t1 = new Thread(new ThreadA("1", a, countDownLatch, b, c));
        t1.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开始创建线程2和线程3");
        Thread t2 = new Thread(new ThreadB("2", b, a, c, cd));
        Thread t3 = new Thread(new ThreadC("3", c, a, b, cd));

        System.out.println("开始创建线程2和线程3完毕");
        t2.start();
        t3.start();

    }

    public static class ThreadA implements Runnable {
        private String name;
        private Object self;
        private Object other1;
        private Object other2;
        private CountDownLatch cd;
        private CountDownLatch cd1;

        private ThreadA(String name, Object self, CountDownLatch cd, Object other1, Object other2) {
            this.name = name;
            this.self = self;
            this.cd = cd;
            this.other1 = other1;
            this.other2 = other2;
        }

        @Override
        public void run() {
            synchronized (self) {
                try {
                    System.out.println("线程" + name + "执行动作1");
                    Thread.sleep(new Random().nextInt(1000));

                    cd.countDown();

                    System.out.println("线程" + name + "阻塞等待被唤醒");
                    self.wait();
                    System.out.println("线程" + name + "唤醒成功");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 同时获得线程2和线程3的锁方能继续执行动作4
                synchronized (other1) {
                    synchronized (other2) {
                        System.out.println("线程" + name + "执行动作4");
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                            System.out.println("线程" + name + "执行动作4完毕");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        other2.notify();
                    }
                    other1.notify();
                }
            }
        }
    }

    public static class ThreadB implements Runnable {
        private String name;
        private Object self;
        private Object lockA;
        private Object lockC;
        private CountDownLatch cd;

        private ThreadB(String name, Object self, Object lockA, Object lockC, CountDownLatch cd) {
            this.cd = cd;
            this.name = name;
            this.self = self;
            this.lockA = lockA;
            this.lockC = lockC;
        }

        @SneakyThrows
        @Override
        public void run() {
            // 先获取线程2自己的锁
            synchronized (self) {
                System.out.println("线程" + name + "执行动作2");
                System.out.println("线程" + name + "执行动作2完毕");
                cd.countDown();

                // 释放线程2自己的锁
                self.wait();
            }
        }
    }

    public static class ThreadC implements Runnable {
        private String name;
        private Object self;
        private Object lockA;
        private Object lockB;
        private CountDownLatch cd;

        private ThreadC(String name, Object self, Object lockA, Object lockB, CountDownLatch cd) {
            this.cd = cd;
            this.name = name;
            this.self = self;
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @SneakyThrows
        @Override
        public void run() {
            // 先获取线程3自己的锁
            synchronized (self) {
                cd.await();
                // 线程3执行动作3
                System.out.println("线程" + name + "执行动作3");
                System.out.println("线程" + name + "执行动作3完毕");

                // 唤醒线程1
//                System.out.println("线程1锁：" + lockA);
                synchronized (lockA) {
                    lockA.notify();
                }

                // 释放线程3自己的锁
                self.wait();
            }
        }
    }
}