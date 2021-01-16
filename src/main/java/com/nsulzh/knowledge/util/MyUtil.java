package com.nsulzh.knowledge.util;

import org.springframework.util.ObjectUtils;



public class MyUtil {
    public static boolean isEmpty(Object o){
        return ObjectUtils.isEmpty(o);
    }
    public static boolean isNotEmpty(Object o){
        return !ObjectUtils.isEmpty(o);
    }
}
