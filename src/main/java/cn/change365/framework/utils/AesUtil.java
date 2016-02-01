package cn.change365.framework.utils;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Jack on 2015/11/17.
 */
public class AesUtil {

    private static final byte[] IV = {16, 66, 35, 13, 34, 12, -35, -4, -8, -99, -15, 78, 90, -18, -99, 100};

    public static byte[] encrypt(String content, String password) {
        try {
            SecretKeySpec key = getKey(password);
            if(key == null){
                return null;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptToStr(String content, String password) {
        byte[] bytes = encrypt(content, password);
//        System.out.println("encryptToStr = " + byte2str(bytes));
        if(bytes == null){
            return null;
        }
        return Base64Util.encode(bytes);
    }

    public static byte[] decrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = getKey(password);
            if(key == null){
                return null;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptFromStr(String encodeStr, String password) {
        byte[] encodeBytes = Base64Util.decodeToBytes(encodeStr);
        byte[] bytes = decrypt(encodeBytes, password);
        if(bytes == null){
            return null;
        }
        return new String(bytes);
    }

    private static SecretKeySpec getKey(String password){
        try {
            byte[] passwdBytes = password.getBytes("utf-8");
            if(passwdBytes.length < 16){
                return null;
            }
            return new SecretKeySpec(generatePasswd(passwdBytes), "AES");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] generatePasswd(byte[] passwdBytes){
        byte[] bytes = new byte[16];
        for(int i = 0; i < 16; i++){
            bytes[i] = passwdBytes[i];
        }
        Arrays.sort(bytes);
        return bytes;
    }

    public static String byte2str(byte[] bytes){
        Integer[] ret = new Integer[bytes.length];
        for(int i = 0; i < bytes.length; i++){
            ret[i] = (int)bytes[i];
        }
        return StringUtils.concatToString(ret, ",");
    }

    private void test(){
        String content = "test aaa";
        String passwd = "passwd tttwerwerwerwer";
        String encrypt = AesUtil.encryptToStr(content, passwd);
        System.out.println("encrypt = [" + encrypt+ "]");
        String decrypt = AesUtil.decryptFromStr(encrypt, passwd);
        System.out.println("decrypt = [" + decrypt + "]");
    }
}
