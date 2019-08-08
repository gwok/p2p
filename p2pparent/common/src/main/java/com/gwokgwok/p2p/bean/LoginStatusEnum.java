package com.gwokgwok.p2p.bean;

public enum LoginStatusEnum implements BizError{
    LOGIN_ERROR("000","用户名密码错误"),
    UNKNOWN_ERROR("001","未知错误"),
    LOGIN_VERIFY_ERROR("002","图片验证码错误"),
    LOGIN_SUCCESS("003","成功登录"),
    NO_LOGIN("004","用户未登录");

    private String code;
    private String desc;

    LoginStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    ;

    @Override
    public String getErrorName() {
        return this.name();
    }

    @Override
    public String getErrorDesc() {
        return this.desc;
    }

    @Override
    public String getErrorCode() {
        return this.code;
    }
}