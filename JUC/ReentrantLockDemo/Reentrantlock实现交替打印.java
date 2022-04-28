/**
 * @ClassName Main
 * @Description 采用Reentrantlock的await/signal精准唤醒实现交替打印
 * @Author Lijuce_K
 * @Date 2022/4/24 17:10
 * @Version 1.0
 **/
@Slf4j(topic = "Mytest")
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Start...");
        AwaitSignal awaitSignal = new AwaitSignal(3);
        Condition aCon = awaitSignal.newCondition();
        Condition bCon = awaitSignal.newCondition();
        Condition cCon = awaitSignal.newCondition();

        new Thread(() -> {
            awaitSignal.print("a", aCon, bCon);
        }).start();
        new Thread(() -> {
            awaitSignal.print("b", bCon, cCon);
        }).start();
        new Thread(() -> {
            awaitSignal.print("c", cCon, aCon);
        }).start();

        try {
            Thread.sleep(1000);
            awaitSignal.lock();
            aCon.signal();
        } finally {
            awaitSignal.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock {
    private int loopNum;

    public AwaitSignal(int loopNum) {
        this.loopNum = loopNum;
    }

    public void print(String content, Condition cur, Condition next) {
        for (int i = 0; i < this.loopNum; i++) {
            try {
                lock();
                cur.await();
                System.out.print(content);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}