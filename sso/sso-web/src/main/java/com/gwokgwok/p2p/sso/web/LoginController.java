package com.gwokgwok.p2p.sso.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.gwokgwok.p2p.bean.LoginStatusEnum;
import com.gwokgwok.p2p.bean.RegistStatusEnum;
import com.gwokgwok.p2p.entry.User;
import com.gwokgwok.p2p.jedis.JedisUtils;
import com.gwokgwok.p2p.sso.service.UserService;
import com.gwokgwok.p2p.utils.Md5Utils;
import com.gwokgwok.p2p.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Resource
    private UserService userServiceImpl;
    @Resource
    private JedisUtils jedisUtils;
    @Resource
    private SerializeConfig serializeConfig;

    @ResponseBody
    @RequestMapping(value="/login",produces= {"text/plain;charset=utf-8"},method = RequestMethod.POST)
    public String login(String username, String password,String verify, HttpServletResponse response, HttpServletRequest request) {
        serializeConfig.configEnumAsJavaBean(LoginStatusEnum.class);
        Cookie[] cookies = request.getCookies();
        boolean cookieFlag=false;
        for(Cookie cookie :cookies){
            if("picCode".equals(cookie.getName())){
                Jedis jedis = jedisUtils.getJedis();
                String value = jedisUtils.get(cookie.getValue(), jedis);
                if(verify.equalsIgnoreCase(value)){
                    cookieFlag=true;
                    jedisUtils.del(cookie.getValue(),jedis);
                    jedisUtils.close(jedis);
                    break;
                }else {
                    jedisUtils.del(cookie.getValue(),jedis);
                    jedisUtils.close(jedis);
                    return JSONObject.toJSONString(LoginStatusEnum.LOGIN_VERIFY_ERROR,serializeConfig);
                }
            }
        }
        if(!cookieFlag){
            return JSONObject.toJSONString(LoginStatusEnum.LOGIN_VERIFY_ERROR,serializeConfig);
        }
        try {
            password= Md5Utils.getMD5(password,username);
            User user= userServiceImpl.findUserByUserNameAndPassword(username,password);
            if(user==null){
                return JSONObject.toJSONString(LoginStatusEnum.LOGIN_ERROR,serializeConfig);
            }
//!!!!!!SecurityUtils.getSubject().getSession().setAttribute("user", user);//设置查询到用户
//!!			UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            //!!SecurityUtils.getSubject().login(token);
            //改为根据用户名查询用户
            //通过shiro 的login方法进行登录
            //	 if (baseJson!=null&&baseJson.getCode()==GlobalFianlVar.SUCCESS) {
            //在这里创建cookie,将key的值放到cookie中,返回给用户

            Cookie cookie=new Cookie("gwok", "gwokgwok"+user.getUserId());
            cookie.setPath("/");
            cookie.setMaxAge(1800);
            response.addCookie(cookie);
            Jedis jedis = jedisUtils.getJedis();
            user.setPassword(null);
            jedisUtils.hmset("gwokgwok"+user.getUserId(), user, jedis);
            jedisUtils.expire("gwokgwok"+user.getUserId(), 1800, jedis);
            jedisUtils.close(jedis);
        } catch (Exception e) {
            //日志记录
            e.printStackTrace();
            return JSONObject.toJSONString(LoginStatusEnum.UNKNOWN_ERROR,serializeConfig);
        }

        return JSONObject.toJSONString(LoginStatusEnum.LOGIN_SUCCESS,serializeConfig);
    }



    @ResponseBody
    @RequestMapping(value = "/getUserInfo" ,method = RequestMethod.POST,produces = {"text/plain;charset=utf-8"})
    public String getUserInfo(String token,String callback){
        serializeConfig.configEnumAsJavaBean(LoginStatusEnum.class);
        String login_success;
        if (StringUtils.isEmpty(token)) {
            return callback+"('"+JSONObject.toJSONString(LoginStatusEnum.NO_LOGIN,serializeConfig)+"')";
        }else {
            Jedis jedis = jedisUtils.getJedis();
            String username = jedis.hget(token, "username");
            if (StringUtils.isEmpty(username)) {
                return callback+"('"+JSONObject.toJSONString(LoginStatusEnum.NO_LOGIN,serializeConfig)+"')";
            }else {
                //更新redis和cookie的有效期,作用是延长用户的登录状态
                //通知小兔子吃萝卜
                //TODO 此处应该发送消息,通知延长用户缓存时间
                login_success=JSONObject.toJSONString(LoginStatusEnum.LOGIN_SUCCESS,serializeConfig);
                login_success=login_success.substring(0,login_success.length()-1)+",\"username\":\""+username+"\"}";
            }
        }
        return callback+"('"+login_success+"')";
    }

}
