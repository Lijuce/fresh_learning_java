import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Main
 * @Description 用Unsafe对象自定义原子类（尝试重写源码）
 * @Author Lijuce_K
 * @Date 2022年5月8日19:50:22
 * @Version 1.0
 **/
@Slf4j(topic = "Mytest")
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        log.info("Start...");
        MyAtomicInteger atomicInteger = new MyAtomicInteger(10000);
        Account.demo(atomicInteger);
    }
}

class MyAtomicInteger implements Account{
    private volatile int value;
    private static final Unsafe unsafe;
    private static long fieldOffset;

    static {
        unsafe = UnsafeAccessor.getUnsafe();
        try {
            fieldOffset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public void decrement(int changeValue) {
        while (true) {
            int prev = this.value;
            int next = prev - changeValue;
            if (unsafe.compareAndSwapInt(this, fieldOffset, prev, next)) {
                break;
            }
        }
    }

    public int getValue() {
        return value;
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    @Override
    public int getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(int withdrawAmount) {
        decrement(withdrawAmount);
    }
}

interface Account {
    int getBalance();

    void withdraw(int withdrawAmount);

    static void demo(Account account) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            threadList.add(new Thread(() -> {
                account.withdraw(2);
            }));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(
                t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        System.out.println("取款后余额：" + account.getBalance());
    }
}