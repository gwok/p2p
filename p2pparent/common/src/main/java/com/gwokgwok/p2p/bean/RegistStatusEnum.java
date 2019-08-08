package com.gwokgwok.p2p.bean;

/**
 * Return value that may be encountered while registering
 * @author gwokgwok
 */
public enum RegistStatusEnum implements BizError{

    PHONE_CODE_ERROR("000","手机验证码错误"),
    PIC_CODE_ERROR("001","图片验证码错误"),
    PHONE_NUM_ERROR("002","手机号不符合规范"),
    PASSWORD_ERROR("003","密码不符合规范"),
    USERNAME_ERROR("004","用户名不符合规范"),
    REGIST_ERROR("005","注册失败"),
    REGIST_SUCCESS("006","注册成功"),
    UNAME_ALREADY_EXIST("007","用户名已存在"),
    USERNAME_AVAILABLE("008","该用户名可用");

    private String code;
    private String desc;

    RegistStatusEnum(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

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
