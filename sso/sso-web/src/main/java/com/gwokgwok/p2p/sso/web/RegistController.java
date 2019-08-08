package com.gwokgwok.p2p.sso.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.gwokgwok.p2p.bean.CommonsStatusEnum;
import com.gwokgwok.p2p.bean.RegistStatusEnum;
import com.gwokgwok.p2p.entry.User;
import com.gwokgwok.p2p.jedis.JedisUtils;
import com.gwokgwok.p2p.sso.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class RegistController {
    @Resource
    private JedisUtils jedisUtils;
    @Resource
    private UserService userServiceImpl;
    @Resource
    private SerializeConfig serializeConfig;

   private final  static Logger logger = LoggerFactory.getLogger(RegistController.class);
    @RequestMapping(value="/regist" ,produces= {"text/plain;charset=utf-8"},method = RequestMethod.POST)
    @ResponseBody
    public String regist(User user,String picCode,HttpServletRequest request) {
        System.out.println("被调用啦");

        System.out.println(user.toString());
        serializeConfig.configEnumAsJavaBean(RegistStatusEnum.class);
        //1获取cookie
        Cookie[] cookies = request.getCookies();
        boolean isHaveCookie=false;
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("picCode".equals(name)) {
                    isHaveCookie=true;
                    String uuid = cookie.getValue();
                    Jedis jedis = jedisUtils.getJedis();
                    String picFromRedis = jedisUtils.get(uuid, jedis);
                    if (picFromRedis != null && picFromRedis.equalsIgnoreCase(picCode)) {//picCode correct if pass
                        jedisUtils.del(uuid, jedis);//delete picCode in redis
                        jedisUtils.close(jedis);//return connection
                    }else {
                        jedisUtils.del(uuid, jedis);// the same as above
                        jedisUtils.close(jedis);
                        return JSONObject.toJSONString(RegistStatusEnum.PIC_CODE_ERROR,serializeConfig);
                    }
                    break;
                }
            }
            if (!isHaveCookie) {
                //no match cookie
                return JSONObject.toJSONString(RegistStatusEnum.PIC_CODE_ERROR,serializeConfig);
            }
        }else {
            return JSONObject.toJSONString(RegistStatusEnum.PIC_CODE_ERROR,serializeConfig);
        }
        //3如果有值,则用这个值作为key去redis中查
        try {
            //判断验证码
            String enumValue = userServiceImpl.addUser(user);
            System.out.println(enumValue);
            switch (enumValue){
                case "\"PIC_CODE_ERROR\"":
                    return JSONObject.toJSONString(RegistStatusEnum.PIC_CODE_ERROR,serializeConfig);
                case "\"PASSWORD_ERROR\"":
                    return JSONObject.toJSONString(RegistStatusEnum.PASSWORD_ERROR,serializeConfig);
                case "\"USERNAME_ERROR\"":
                    return JSONObject.toJSONString(RegistStatusEnum.USERNAME_ERROR,serializeConfig);
                case "\"REGIST_SUCCESS\"":
                    return JSONObject.toJSONString(RegistStatusEnum.REGIST_SUCCESS,serializeConfig);
                case "\"UNAME_ALREADY_EXIST\"":
                    return JSONObject.toJSONString(RegistStatusEnum.UNAME_ALREADY_EXIST,serializeConfig);
                default:
                    return JSONObject.toJSONString(RegistStatusEnum.REGIST_ERROR,serializeConfig);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //日志记录错误
            logger.info("用户添加出错");
        }

        return JSONObject.toJSONString(RegistStatusEnum.REGIST_ERROR,serializeConfig);
    }
    @ResponseBody
    @RequestMapping(value = "ifexist",method = RequestMethod.POST,produces= {"text/plain;charset=utf-8"})
    public  String  ifexist(String verifyVal){
        try {
            User userByUserName = userServiceImpl.findUserByUserName(verifyVal);
            System.out.println(userByUserName);
            if(userByUserName != null){
                return RegistStatusEnum.UNAME_ALREADY_EXIST.getErrorCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("用户查询有误");
            return RegistStatusEnum.UNAME_ALREADY_EXIST.getErrorCode();
        }
        return RegistStatusEnum.USERNAME_AVAILABLE.getErrorCode();
    }
}
