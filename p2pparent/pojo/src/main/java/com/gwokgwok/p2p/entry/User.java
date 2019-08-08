package com.gwokgwok.p2p.entry;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer userId;
    private String username;
    private String password;
    private String nickname;
    private String phoneNum;
    private String email;
    private String referrer;
    private String registDate;
    private Integer status;
    private Integer userType;
    private Integer realNameStatus;//实名状态
    private Integer phoneStatus;//手机状态
    private Integer emailStatus;//邮箱



}
