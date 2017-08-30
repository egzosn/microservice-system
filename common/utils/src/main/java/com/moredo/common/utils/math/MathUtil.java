package com.moredo.common.utils.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: chenlc
 * Date: 14-4-12
 * Time: 下午1:03
 * To change this template use File | Settings | File Templates.
 */
public class MathUtil {
    // 默認除法運算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精確的加法運算。
     *
     * @param v1
     *            String 被加數
     * @param v2
     *            String 加數
     * @return double 兩個參數的和
     */
    public static double add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {

        return v1.add(v2);
    }
    /**
     * 提供精確的加法運算。
     *
     * @param v1
     *            被加數
     * @param v2
     *            加數
     * @return 兩個參數的和
     */
    public static double add(double v1, double v2) {
        return add(Double.toString(v1), Double.toString(v2));
    }

    /**
     * 提供精確的減法運算。
     *
     * @param v1
     *            String 被減數
     * @param v2
     *            String 減數
     * @return double 兩個參數的差
     */
    public static double subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精確的減法運算。
     *
     * @param v1
     *            被減數
     * @param v2
     *            減數
     * @return 兩個參數的差
     */
    public static double subtract(double v1, double v2) {
        return subtract(Double.toString(v1), Double.toString(v2));
    }

    /**
     * 提供精確的乘法運算。
     *
     * @param v1
     *            被乘數
     * @param v2
     *            乘數
     * @return 兩個參數的積
     */
    public static double multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }
    public static BigDecimal multiplyforBigDecimal(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2);
    }
    /**
     * 提供精確的乘法運算。
     *
     * @param v1
     *            被乘數
     * @param v2
     *            乘數
     * @return 兩個參數的積
     */
    public static double multiply(double v1, double v2) {
        return multiply(Double.toString(v1), Double.toString(v2));
    }

    /**
     * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指 定精度，以後的數字四捨五入。
     *
     * @param v1
     *            被除數
     * @param v2
     *            除數
     * @param scale
     *            表示表示需要精確到小數點以後幾位。
     * @return 兩個參數的商
     */
    public static double divide(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相對）精確的除法運算，當發生除不盡的情況時，精確到 小數點以後10位元，以後的數字四捨五入。
     *
     * @param v1
     *            被除數
     * @param v2
     *            除數
     * @return 兩個參數的商
     */
    public static double divide(String v1, String v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指 定精度，以後的數字四捨五入。
     *
     * @param v1
     *            被除數
     * @param v2
     *            除數
     * @param scale
     *            表示表示需要精確到小數點以後幾位。
     * @return 兩個參數的商
     */
    public static double divide(double v1, double v2, int scale) {
        return divide(Double.toString(v1), Double.toString(v2), scale);
    }

    /**
     * 提供（相對）精確的除法運算，當發生除不盡的情況時，精確到 小數點以後10位元，以後的數字四捨五入。
     *
     * @param v1
     *            被除數
     * @param v2
     *            除數
     * @return 兩個參數的商
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供精確的小數位四捨五入處理。
     *
     * @param v
     *            需要四捨五入的數位
     * @param scale
     *            小數點後保留幾位
     * @return 四捨五入後的結果
     */
    public static double round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 保留小数scale个0
     */
    public static String roundCN(String v, int scale) {
        String parten = "#.";
        for (int i = 0; i < scale; i++) {
            parten = parten + "0";
        }
        DecimalFormat decimal = new DecimalFormat(parten);
        return decimal.format(round(v, scale));

    }

    /**
     * 提供精確的小數位四捨五入處理。
     *
     * @param v
     *            需要四捨五入的數位
     * @param scale
     *            小數點後保留幾位
     * @return 四捨五入後的結果
     */
    public static double round(double v, int scale) {
        return round(Double.toString(v), scale);
    }
    public static BigDecimal round(BigDecimal v, int scale) {
        return v.setScale(scale, BigDecimal.ROUND_HALF_UP)  ;
    }

    public static int round(double v) {

        return new BigDecimal(v).setScale(0, BigDecimal.ROUND_HALF_UP)
                .intValue();
    }

    public static long roundbai(long v,int scale){
        long i=v/scale;
        return (i+1)*scale;
    }

}
