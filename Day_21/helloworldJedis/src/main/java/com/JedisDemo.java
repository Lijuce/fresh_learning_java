package com;

import redis.clients.jedis.Jedis;

public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("key", "lijuce");
        String res = jedis.get("key");
        System.out.println(res);
        jedis.close();
    }
}


