package com.atguigu.gmall0325.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    /**
     *
     * 1.创建JedisPool
     * 2.获取Jedis
     */

    private JedisPool jedisPool;

    public void initJedisPool(String host, int port, int timeOut, int database){

        //创建配置连接池的参数类
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置连接池最大核心数
        jedisPoolConfig.setMaxTotal(200);
        //设置等待事件
        jedisPoolConfig.setMaxWaitMillis(10*1000);
        //最少剩余数
        jedisPoolConfig.setBlockWhenExhausted(true);
        //设置当用户获取到jedis时，做自检看当前获取到的jedis是否可以使用
        jedisPoolConfig.setTestOnBorrow(true);

        jedisPool = new JedisPool(jedisPoolConfig,host,port,timeOut);
    }

    //获取Jedis
    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
