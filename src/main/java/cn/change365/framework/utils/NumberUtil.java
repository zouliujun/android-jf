package cn.change365.framework.utils;

import java.math.BigDecimal;

/**
 * Created by Jack on 2015/6/19.
 */
public class NumberUtil {

    /**
     *  String 转 BigDecimal
     * @param data 原始字符串
     * @param decimal 保留多少位小数
     * @return
     */
    public static BigDecimal getBigDecimalFromString(String data, int decimal){
        BigDecimal temp = new BigDecimal(data);
        return temp.setScale(decimal, BigDecimal.ROUND_HALF_UP);
    }

    public static double getDoubleDecimal(double data, int decimal){
        BigDecimal temp = new BigDecimal(data);
        return temp.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
