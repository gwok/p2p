package com.gwokgwok.p2p.piccode.web;

import cn.dsna.util.images.ValidateCode;
import com.gwokgwok.p2p.jedis.JedisUtils;
import com.gwokgwok.p2p.utils.StringUtils;
import com.gwokgwok.p2p.utils.UuidUtils;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/verify")
public class VerifyController {
    @Resource
    private JedisUtils jedisUtils;

    /**
     * get the verify
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void getVerify(HttpServletRequest request, HttpServletResponse response){
        //ValidateCode:width,height,codeCount,lineCount
        try {
            ValidateCode vc = new ValidateCode(120, 30, 4, 13);
            String code = vc.getCode();//get the content
            String uuid = UuidUtils.getUUID();//get the uuid
            Jedis jedis = jedisUtils.getJedis(); // connect to redis-server
            jedisUtils.set(uuid, code, jedis);
            jedisUtils.expire(uuid, 300, jedis);//set the expiration time to the key for 300 seconds
            jedisUtils.close(jedis);//return the connection to the connection pool
            Cookie cookie = new Cookie("picCode", uuid);
            cookie.setPath("/");
            cookie.setMaxAge(300);
            response.addCookie(cookie);
            vc.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
