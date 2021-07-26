package schedule;

import lock.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @ClassName ScheduleDemo
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/7/26 16:19
 * @Version 1.0
 **/
@Component
@EnableScheduling
public class ScheduleDemo {
    private String sourceKey = "redis:schedule:test:key";

    private void sendEmail (String serviceKey) throws InterruptedException {
        if (!RedisLock.tryLock(serviceKey, 20)) {
            // 获取锁失败，此时不重复执行
            return;
        }

        try (Jedis jedis = new Jedis("localhost", 6379)) {
            // 从redis读取来模拟发送的批次
            Integer sendPatch = 0;
            Object val = jedis.get(sourceKey);
            if (val != null) {
                sendPatch = Integer.parseInt(val.toString());
            }

            Thread.sleep(2000);
            System.out.println("批次[" + sendPatch +"]====发送邮件====" + serviceKey);
            // 批次加1
            jedis.incr(sourceKey);

            // 释放锁
            RedisLock.tryUnlock(serviceKey);
        }
    }

    // 模拟service
    @Scheduled(cron = "0/2 * * ? * *") // 【cron改为后面的时间】
    public void execute () throws InterruptedException {
        this.sendEmail("service");
    }
}
