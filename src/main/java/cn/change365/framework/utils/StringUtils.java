package cn.change365.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class StringUtils {
	
	public static <T> Set<T> arrayToSet(T[] strs){
		Set<T> mySet = new HashSet<T>(arrayToList(strs));
		return mySet;
	}

	public static <T> T[] setToArray(Set<T> set, Class<T> clazz){
		T[] ret = (T[]) Array.newInstance(clazz, set.size());
		set.toArray(ret);
		return ret;
	}

	public static <T> String concatToString(T[] array, String split){
		StringBuilder sb = new StringBuilder();
		for(T item : array){
			sb.append(item).append(split);
		}
		String ret = sb.toString();
		if(ret.endsWith(split)){
			ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}

	public static <T> List<T> arrayToList(T[] strs){
		return Arrays.asList(strs);
	}
	
//	public static String concatToString(String[] array, String split){
//		return concatToString(array, split);
//	}
	
	public static <T> String concatToString(Collection<T> array, String split){
		StringBuilder sb = new StringBuilder();
		for(T item : array){
			sb.append(item).append(split);
		}
		String ret = sb.toString();
		if(ret.endsWith(split)){
			ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}
	
	public static boolean isEmptyStr(String str){
		return str == null || str.length() == 0;
	}
	

	public static InputStream getInputStreamFromString(String str){
		InputStream stream = new ByteArrayInputStream(str.getBytes(Charsets.UTF_8));
		return stream;
	}

	public static String getStringFromInputStream(InputStream is){
		Scanner scanner = new Scanner(is, "UTF-8");
		String text = scanner.useDelimiter("\\A").next();
		scanner.close();
		return text;
	}

	public static String connectStr(Object...args){
		StringBuilder sb = new StringBuilder();
		for(Object item : args){
			sb.append(item);
		}
		return sb.toString();
	}

	public static String toOneLineEntityString(Object o){
		return toEntityString(o, true);
	}

	public static String toMultiLineEntityString(Object o){
		return toEntityString(o, false);
	}

	private static String toEntityString(Object o, boolean oneLine){
		StringBuilder sb = new StringBuilder();
		String next = "";

		if(!oneLine){
			next = "\n";
		}
		Class<?> thisClass = o.getClass();
		Field[] aClassFields = thisClass.getDeclaredFields();
		sb.append(thisClass.getSimpleName()).append(" [ ").append(next);
		try {
			for(Field f : aClassFields){
				f.setAccessible(true);
				sb.append(f.getName()).append(" = ").append(f.get(o)).append(", ").append(next);
			}
		} catch (Exception e) {
			sb.append(" toMultiLineEntityString error ");
		}
		int lastDot = sb.length() - 2;
		if(!oneLine){
			lastDot--;
		}
		if(sb.lastIndexOf(",") == lastDot){
			sb.delete(lastDot, sb.length());
		}
		sb.append(" ]\n");
		return sb.toString();
	}

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	//str.length==0 等价于 str == null
	public static boolean isLooseEqual(String str1, String str2){
		if(str1 == null){
			str1 = "";
		}
		if(str2 == null){
			str2 = "";
		}
		return str1.equals(str2);
	}
	

}
