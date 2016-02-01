package cn.change365.framework.utils;

import android.util.Base64;

/**
 * Created by Jack on 2015/8/12.
 */
public class Base64Util {

    public static String encode(String originStr){
        return new String(Base64.encode(originStr.getBytes(), Base64.DEFAULT));
    }

    public static String encode(byte[] bytes){
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    public static String decode(String encodeStr){
        return new String(Base64.decode(encodeStr.getBytes(), Base64.DEFAULT));
    }

    public static byte[] decodeToBytes(String encodeStr){
        return Base64.decode(encodeStr.getBytes(), Base64.DEFAULT);
    }
}
