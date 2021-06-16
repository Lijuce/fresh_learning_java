package com.jedis;

import com.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisDataException;

public class JedisService {
    private long num;
    private String id;

    public JedisService(String id, long num){
        this.id = id;
        this.num = num;
    }

    public void service(){
//        Jedis jedis = new Jedis("localhost", 6379);
        Jedis jedis = JedisUtil.getJedis();
        String value = jedis.get("compid:"+id);
        try{
            if (null == value) {
                // 不存在该键，进行创建
                jedis.setex("compid:"+id, (long) 5, Long.MAX_VALUE - num + "");
            } else {
                // 存在该键，那么调用业务，进行自增操作
                Long val = jedis.incr("compid:"+id);
                System.out.println("compid:"+id+"调用业务...." + val);
            }
        }catch (JedisDataException e){
            System.out.println("使用次数结束，请升级用户");
        }finally {
            jedis.close();
        }
    }
}