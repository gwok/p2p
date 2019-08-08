package com.gwokgwok.p2p.jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.gwokgwok.p2p.bean.RegistStatusEnum;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.shiro.util.ByteSource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class test01 {
    public static void main(String args[]){
        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(RegistStatusEnum.class);
        String s = JSON.toJSONString(RegistStatusEnum.PIC_CODE_ERROR, config);
        String username = "卧槽";
        System.out.println(s.substring(0,s.length()-1)+",\"username\":\""+username+"\"}");




    }
}
class Abc{

    private String name;
    private Integer age;

    public Abc() {
    }

    public Abc(Integer age) {
        this.age = age;
    }

    public Abc(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Abc(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        System.out.println("我操");
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Abc{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
