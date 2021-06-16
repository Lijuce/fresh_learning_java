package com.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtil {
    private static JedisPool jedisPool;
    private static String host;
    private static int port;
    private static int MaxTotal;
    private static int MaxIdle;
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
        host = resourceBundle.getString("redis.host");
        port = Integer.parseInt(resourceBundle.getString("redis.port"));
        MaxTotal = Integer.parseInt(resourceBundle.getString("redis.MaxTotal"));
        MaxIdle = Integer.parseInt(resourceBundle.getString("redis.MaxIdle"));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(MaxTotal);  // 最大连接池数量
        jedisPoolConfig.setMaxIdle(MaxIdle);  // 最大活动数量
        jedisPool = new JedisPool(jedisPoolConfig, host, port);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
