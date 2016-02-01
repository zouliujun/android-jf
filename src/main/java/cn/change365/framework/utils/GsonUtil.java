package cn.change365.framework.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Jack on 2015/8/12.
 */
public class GsonUtil {
    private static Gson gson = new Gson();

    public static String getJSONString(Object o){
        return gson.toJson(o);
    }

    public static String getJSONString(Object o, Type type){
        return gson.toJson(o, type);
    }

    public static final Type mapIntStr = new TypeToken<Map<Integer, String>>(){}.getType();

    public static <T> T fromJSON(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJSON(String json, Type type){
        return gson.fromJson(json, type);
    }
}
