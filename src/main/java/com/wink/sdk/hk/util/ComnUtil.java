package com.wink.sdk.hk.util;

import java.util.*;

/**
 * @author gongjunlang
 */
public class ComnUtil {

    /**
     * 判断一个对象是否为空
     * <p>
     * 以下情况为true
     * <p>
     * 对象为null
     * <p>
     * 字符串对象为""或者length为0
     * <p>
     * 集合对象size为0
     * <p>
     * 数组对象length为0
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return ((String) obj).trim().equals("");
        } else if (obj instanceof StringBuilder) {
            return ((StringBuilder) obj).length() == 0;
        } else if (Collection.class.isAssignableFrom(obj.getClass())) {
            return ((Collection<?>) obj).size() == 0;
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() == 0;
        } else if (obj.getClass().isArray()) {
            if (obj instanceof byte[]) {
                return ((byte[]) obj).length == 0;
            }
            if (obj instanceof short[]) {
                return ((short[]) obj).length == 0;
            }
            if (obj instanceof int[]) {
                return ((int[]) obj).length == 0;
            }
            if (obj instanceof long[]) {
                return ((long[]) obj).length == 0;
            }
            if (obj instanceof char[]) {
                return ((char[]) obj).length == 0;
            }
            if (obj instanceof float[]) {
                return ((float[]) obj).length == 0;
            }
            if (obj instanceof double[]) {
                return ((double[]) obj).length == 0;
            }
            if (obj instanceof boolean[]) {
                return ((boolean[]) obj).length == 0;
            }
            return ((Object[]) obj).length == 0;
        } else if (obj instanceof StringBuffer) {
            return ((StringBuffer) obj).length() == 0;
        }
        return false;
    }

    /**
     * 判断一个对象是否为空
     * <p>
     * 以下情况为true
     * <p>
     * 对象为null
     * <p>
     * 字符串对象为""或者length为0
     * <p>
     * 集合对象size为0
     * <p>
     * 数组对象length为0
     * <p>
     * 数字对象值为0
     *
     * @param obj
     * @return
     */
    public static boolean isEmptyOrZero(Object obj) {
        if (Number.class.isAssignableFrom(obj.getClass())) {
            if (((Number) obj).intValue() == 0 || ((Number) obj).doubleValue() == 0 || ((Number) obj).longValue() == 0
                    || ((Number) obj).floatValue() == 0 || ((Number) obj).shortValue() == 0
                    || ((Number) obj).byteValue() == 0) {
                return true;
            }
        }
        return isEmpty(obj);
    }

    /**
     * 适用于非复合主键数据
     *
     * @param key
     * @return
     */
    public static String getStringWithSingleQuotes(String key, String split) {
        String[] ids = key.split(split);
        String result = "";
        for (String id : ids) {
            result += ",'" + id + "'";
        }
        if (result.length() > 0) {
            return result.substring(1);
        }
        return null;
    }

    public static List<String> string2List(String key, String split) {
        if (isEmpty(key)) {
            return new ArrayList<String>();
        }
        return Arrays.asList(key.split(split));
    }

    /**
     * @param src
     * @param concat
     * @return
     */
    public static <T> T[] concatArray(T[] src, T[] concat) {
        List<T> srcList = new ArrayList<T>(Arrays.asList(src));
        List<T> concatList = new ArrayList<T>(Arrays.asList(concat));
        srcList.addAll(concatList);
        return srcList.toArray(src);
    }

    // public static Object[] concatArray(Object[] src, Object[] concat) {
    // List<Object> srcList = new ArrayList<Object>(Arrays.asList(src));
    // List<Object> concatList = new ArrayList<Object>(Arrays.asList(concat));
    // srcList.addAll(concatList);
    // return srcList.toArray(src);
    // }
}
