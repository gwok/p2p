package com.gwokgwok.p2p.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public interface JedisTools {
    String get(String key, Jedis jedis);
    String set(String key,String vlaue,Jedis  jedis);
    Boolean exists(String key,Jedis  jedis);
    Long expire(String key,int seconds,Jedis  jedis);
    Long ttl(String key,Jedis  jedis);
    Long incr(String key,Jedis  jedis);
    Long del(String key,Jedis  jedis);
    String hmset(String key,Object value,Jedis  jedis) throws Exception;
    String hget(String bigKey,String smallKey,Jedis  jedis);
    Map<String, String> hgetAll(String key, Jedis  jedis);
    Long hset(String key,String field,String value,Jedis jedis);
    Jedis getJedis();
    List<String> hmget(String key, Jedis jedis, String ...fields);
    String hmset(String key,Map<String, String> map,Jedis jedis);
    void close(Jedis jedis);
}
