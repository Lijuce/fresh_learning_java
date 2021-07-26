package lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @ClassName RedisLock
 * @Description TODO
 * @Author Lijuce_K
 * @Date 2021/7/26 15:21
 * @Version 1.0
 **/
public class RedisLock {
    private static String REDIS_LOCK_KEY = "redis:lock:key";

    public static void setRedisLockKey(String redisLockKey) {
        REDIS_LOCK_KEY = redisLockKey;
    }

    /**
     * 尝试获取锁
     * @param ov 指定锁标识，锁的唯一值，用于区分锁的所有者身份
     * @param timeOut 获取锁的超时时间（避免死锁）
     * @return
     */
    public static boolean tryLock(String ov, long timeOut) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            // set nx ex
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.ex(timeOut);
            // set [key] nx ex [timeout]
            Object val = jedis.set(REDIS_LOCK_KEY, ov, setParams);
            return val != null;
        }
    }

    /**
     * 使用lua脚本释放锁
     * @param ov 释放之前先确定解锁人的身份，所以要用到lua的原子特性
     * @return
     */
    public static boolean tryUnlock(String ov) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            // Lua脚本定义释放锁
            String DISTRIBUTE_LOCK_SCRIPT_UNLOCK_VAL = "if (redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";
            String sha1 = jedis.scriptLoad(DISTRIBUTE_LOCK_SCRIPT_UNLOCK_VAL);
            String[] keys = {REDIS_LOCK_KEY};
            String[] args = {ov};
            Integer val = Integer.parseInt(jedis.evalsha(sha1,new ArrayList<>(Arrays.asList(keys)),new ArrayList<>(Arrays.asList(args))).toString());
            return val > 0;
        }
    }
}
