package com.gwokgwok.p2p.jedis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JedisUtils implements JedisTools{
    @Resource
    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPools) {
        jedisPool = jedisPools;
    }
    @Override
    public Jedis getJedis() {

        return jedisPool.getResource();
    }
    @Override
    public void close(Jedis jedis) {
        jedis.close();
    }
    @Override
    public String get(String key,Jedis jedis) {
        return jedis.get(key);
    }
    @Override
    public String set(String key, String value, Jedis jedis) {
        return jedis.set(key, value);
    }
    @Override
    public Boolean exists(String key, Jedis jedis) {
        return jedis.exists(key);
    }
    @Override
    public Long expire(String key, int seconds, Jedis jedis) {
        return jedis.expire(key,seconds);
    }
    @Override
    public Long ttl(String key, Jedis jedis) {
        return jedis.ttl(key);
    }
    @Override
    public Long incr(String key, Jedis jedis) {
        return jedis.incr(key);
    }
    @Override
    public Long del(String key, Jedis jedis) {
        return jedis.del(key);
    }

    @Override
    public String hmset(String key, Object object, Jedis jedis) throws Exception {
        try {
            Map<String, String> hash=new HashMap<>();
            Class clazz = object.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();//获取所有的私有变量
            for (Field field : declaredFields) {
                String name = field.getName();
                field.setAccessible(true);
                Object values = field.get(object);//获取值
                //每次获取一个,添加到map中
                if (values!=null) {
                    hash.put(name, values.toString());
                }
            }
            //将属性的map批量添加到redis中
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public String hget(String bigKey, String smallKey, Jedis jedis) {
        return jedis.hget(bigKey, smallKey);
    }
    @Override
    public Map<String, String> hgetAll(String key, Jedis jedis) {
        return jedis.hgetAll(key);
    }
    @Override
    public Long hset(String key, String field, String value, Jedis jedis) {
        return jedis.hset(key, field, value);
    }
    @Override
    public List<String> hmget(String key, Jedis jedis,String... fields) {
        return jedis.hmget(key, fields);
    }
    @Override
    public String hmset(String key, Map<String, String> map, Jedis jedis) {
        return jedis.hmset(key, map);
    }
}
