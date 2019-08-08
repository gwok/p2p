package com.gwokgwok.p2p.sso.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.gwokgwok.p2p.bean.RegistStatusEnum;
import com.gwokgwok.p2p.dao.UserMapper;
import com.gwokgwok.p2p.entry.User;
import com.gwokgwok.p2p.sso.service.UserService;
import com.gwokgwok.p2p.utils.Md5Utils;
import com.gwokgwok.p2p.utils.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;


    public String addUser(User user) throws Exception {
        //if username is null or "".
        if(StringUtils.isEmpty(user.getUsername())){
           return JSONObject.toJSONString(RegistStatusEnum.USERNAME_ERROR);
        }
        //if the username can`t matches the regular
        // regular: username must be 4 to 16 characters in letters, _- or numbers.
        if(!user.getUsername().matches("^[a-zA-Z0-9_-]{4,16}$")){
            return JSONObject.toJSONString(RegistStatusEnum.USERNAME_ERROR);
        }
        //if password is null or "".
        if(StringUtils.isEmpty(user.getPassword())){
            return JSONObject.toJSONString(RegistStatusEnum.USERNAME_ERROR);
        }
        //if the password can`t matches the regular.
        // regular: password must be 6 to 20 characters in letters or number,but not in pure numbers or letters..
        if(!user.getPassword().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")){
            return JSONObject.toJSONString(RegistStatusEnum.USERNAME_ERROR);
        }
        //if username already exist
        User user1 = userMapper.selectByUsername(user.getUsername());
        //System.out.println(user1.toString());
        if(user1 !=null){
            return JSONObject.toJSONString(RegistStatusEnum.UNAME_ALREADY_EXIST);
        }
        //TODO  phonenumber

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setRegistDate(dateFormat.format(new Date()));
        user.setPassword(Md5Utils.getMD5(user.getPassword(),user.getUsername()));
        int insert = userMapper.insert(user);
        System.out.println(insert);
        if(insert<=0){
            throw new RuntimeException("用户插入出错");
        }
        System.out.println(user.getUserId());
        if(user.getUserId() !=null && user.getUserId() !=0)
            return JSONObject.toJSONString(RegistStatusEnum.REGIST_SUCCESS); //regist success
        return JSONObject.toJSONString(RegistStatusEnum.REGIST_ERROR);// regist error
    }

    public User findUserByUserNameAndPassword(String username, String password) throws Exception {
        return userMapper.selectUserByUserNameAndPassword(username,password);
    }

    public User findUserByUserName(String username) throws Exception {
        return userMapper.selectByUsername(username);
    }
}
