package com.gwokgwok.p2p.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.DigestUtils;

public class Md5Utils {


    /**
     * get the MD5 value
     * @param source
     * @return MD5
     */
    public static String getMD5(String source) {
        if (source==null) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }

    /**
     *  using salt to hash
     * @param source
     * @param salt
     * @return
     */
    public static String getMD5(String source,String salt) {
        if (source==null) {
            return null;
        }
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        return new SimpleHash("MD5", source, byteSource, 512).toString();

        //1cha
        //2查
        //1更新
        //2更新
    }
}
