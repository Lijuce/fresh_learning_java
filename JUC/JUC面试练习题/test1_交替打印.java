/*
* 练习题内容：三个线程1、2、3，对应A、B、C字母，各自打印10次，严格轮流执行打印
* 效果：
*   A
*   B
*   C
*   A
*   B
*   C
*   ...
*   ...
* */

/**
 * @ClassName ABCThread
 * @Description 三个线程轮流打印A、B、C多线程同步方案
 * @Author Lijuce_K
 * @Date 2022/2/20 22:39
 * @Version 1.0
 **/
public class ABCThread {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadPrinter pa = new ThreadPrinter("A", c, a);
        ThreadPrinter pb = new ThreadPrinter("B", a, b);
        ThreadPrinter pc = new ThreadPrinter("C", b, c);

        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();

    }

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
                System.out.println(prev);
                synchronized (prev) {
                    // 再获取 self 锁
                    System.out.println(self);
                    synchronized (self) {
                        System.out.println(name);
                        count --;

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
}


/**
 * 这个方法更为简洁
 * 采用flag来记录打印标记
 */
@Slf4j(topic = "Mytest")
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Start...");
        WaitNotify wn = new WaitNotify(1, 3);
        new Thread(() -> {
            wn.print("a", 1, 2);
        }).start();
        new Thread(() -> {
            wn.print("b", 2, 3);
        }).start();
        new Thread(() -> {
            wn.print("c", 3, 1);
        }).start();
    }
}

class WaitNotify {
    /**
     * 等待标记
     */
    private int flag;

    /**
     * 循环次数
     */
    private int loopNum;

    public WaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String content, int waitFlag, int nextFlag) {
        for (int i = 0; i < this.loopNum; i++) {
            synchronized (this) {
                // 当等待标记不符合要打印的内容，则进行等待
                while (this.flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(content);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}