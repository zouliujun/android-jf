package cn.change365.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Set;

public class SharedPreferencesUtil {
	
	public static String[] getStringSet(SharedPreferences p, String key){
		String dataStr = p.getString(key, null);
		if(StringUtils.isEmptyStr(dataStr)){
			return null;
		}
		String[] data = dataStr.split(",");
		return data;
	}
	
	public static void putStringSet(SharedPreferences.Editor editor, String key, String[] values){
        editor.putString(key, StringUtils.concatToString(values, ","));
	}
	
	public static void putStringSet(SharedPreferences.Editor editor, String key, Set<String> values){
        editor.putString(key, StringUtils.concatToString(values, ","));
	}

	public static void putObject(SharedPreferences.Editor editor, String key, Object value, Type type){
		String temp = GsonUtil.getJSONString(value, type);
		temp = Base64Util.encode(temp);
		editor.putString(key, temp);
	}

	public static void putObject(SharedPreferences.Editor editor, String key, Object value){
		String temp = GsonUtil.getJSONString(value);
		temp = Base64Util.encode(temp);
		editor.putString(key, temp);
	}

	public static <T> T getObject(SharedPreferences p, String key, Type type){
		String dataStr = p.getString(key, null);
		if(StringUtils.isEmptyStr(dataStr)){
			return null;
		}
		String temp = Base64Util.decode(dataStr);
		return GsonUtil.fromJSON(temp, type);
	}
	
	public static SharedPreferences getSharedPreferences(String name, Context context){
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public static void save(SharedPreferences sp, String key, Object value, Type type){
		SharedPreferences.Editor editor = sp.edit();
		if(value instanceof String){
			editor.putString(key, (String) value);
		}else if(value instanceof Integer){
			editor.putInt(key, (Integer) value);
		}else if(value instanceof Boolean){
			editor.putBoolean(key, (Boolean) value);
		}else if(value instanceof String[]){
			putStringSet(editor, key, (String[])value);
		}else{
			if(type != null){
				putObject(editor, key, value, type);
			}else{
				putObject(editor, key, value);
			}
		}
		editor.apply();
	}

	public static void remove(SharedPreferences sp, String key){
		sp.edit().remove(key).apply();
	}

	public static Object get(SharedPreferences sp, String key, Type c){
		if(c == null){
			return null;
		}
		if(c == String[].class){
			return getStringSet(sp, key);
		}else if(c == String.class){
			return sp.getString(key, null);
		}else if(c == Integer.class){
			return sp.getInt(key, -1);
		}else if(c == Boolean.class){
			return sp.getBoolean(key, false);
		}else{
			return getObject(sp, key, c);
		}
	}

}
