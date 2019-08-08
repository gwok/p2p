package com.gwokgwok.p2p.bean;

/**
 * Return value that may be encountered while registering
 * @author gwokgwok
 */
public enum CommonsStatusEnum implements  BizError {

    ERROR("0000","未知失败"),
    INVALID("0001","失效"),
    SUCCESS("0002","成功"),
    EFFECTIVE("0003","有效"),
    NETWORK_ERROR("004","网络错误"),
    NOTLOGIN("0005","用户未登录");

    private String code;
    private String desc;

    CommonsStatusEnum(String code,String desc){

        this.code=code;
        this.desc=desc;

    }
    @Override
    public String getErrorName() {
        return this.name();
    }

    @Override
    public String getErrorDesc() {
        return null;
    }

    @Override
    public String getErrorCode() {
        return null;
    }
}
