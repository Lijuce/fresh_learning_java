import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Main
 * @Description 用于实现保护性暂停设计模式的Demo示例
 * 业务场景：模居民收信和邮递员送信，一个邮递员对应服务一个居民，以邮箱作为中间过渡件。
 * @Author Lijuce_K
 * @Date 2022/4/24 17:10
 * @Version 1.0
 **/
@Slf4j(topic = "Mytest")
public class Main {
    static int count = 1;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Start...");
        for (int i = 0; i < 5; i++) {
            new Person().start();
        }

        TimeUnit.SECONDS.sleep(1);

        Set<Integer> ids = MailBoxed.getIds();
        for (int i: ids) {
            new Postman(i, i+"xxyy").start();
        }
    }
}

@Slf4j
class Postman extends Thread {
    private int id;
    private String mailContent;

    public Postman(int id, String mailContent) {
        this.id = id;
        this.mailContent = mailContent;
    }

    @Override
    public void run() {
        GuardSuspendObj guardSuspendObj = MailBoxed.getGuardSuspendObj(id);
        log.info("邮递员{}开始送信...", Thread.currentThread().getId());
        try {
            // 模拟送信时延
            Thread.sleep(200);
            log.info("邮递员{}送信完毕，信件Id:{}, 信件内容：{}", Thread.currentThread().getId(), this.id, this.mailContent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        guardSuspendObj.complete(this.mailContent);
    }
}

@Slf4j
class Person extends Thread {
    @Override
    public void run() {
        GuardSuspendObj guardSuspendObj = MailBoxed.createGuardSuspendObj();
        log.info("居民{}开始接收信件。。。", guardSuspendObj.getId());
        Object mailContent = guardSuspendObj.get(2000);
        log.info("居民{}成功接收信件，信件内容：{}", guardSuspendObj.getId(), mailContent);
    }
}

class MailBoxed {
    private static int id = 1;

    private static Map<Integer, GuardSuspendObj> mailBoxes = new ConcurrentHashMap<>();

    public static synchronized int generateId() {
        return id++;
    }

    public static GuardSuspendObj getGuardSuspendObj(int id) {
        return mailBoxes.remove(id);
    }
    public static GuardSuspendObj createGuardSuspendObj() {
        GuardSuspendObj guardSuspendObj = new GuardSuspendObj(generateId());
        mailBoxes.put(guardSuspendObj.getId(), guardSuspendObj);
        return guardSuspendObj;
    }

    public static Set<Integer> getIds() {
        return mailBoxes.keySet();
    }
}

class GuardSuspendObj {
    private int id;

    private Object response;

    public GuardSuspendObj(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Object get(long timeout) {
        synchronized (this) {
            long lastTime = System.currentTimeMillis();
            long passedTime = 0;
            while (this.response == null) {
                long waitTime = timeout-passedTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    // 1. 不超时；
                    // 2. 正常超时：
                    // 3. 虚假唤醒：
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - lastTime;

            }
            return this.response;
        }
    }

    public void complete(Object obj) {
        synchronized (this) {
            this.response = obj;
            this.notify();
        }
    }
}

