package com.gwokgwok.p2p.utils;

import com.gwokgwok.p2p.bean.BizError;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    /**
     * Check whether the given {@code String} is empty.
     * @param source
     * @return true if the param is null or the empty string
     */
    public static boolean isEmpty(String source) {
        return source==null||source.trim().equals("");
    }
    public  static String enumToJsonString(BizError bizError){
        Map<String,Integer> map = new HashMap<>();

        return "";
    }

}
