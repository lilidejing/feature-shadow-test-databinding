package com.kye.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 运费计算工具类
 */
public class FreightCalUtil {

    // 目前限制的超长值，大于等于此值即为超长
    public static final int OVER_LONG_LIMIT = 160;

    /**
     * 是否显示超重
     *
     * @param type 类型
     * @return true: “当天达”、“次日达”;false 其他方式
     */
    public static boolean isShowOverweight(String type) {
        if ("当天达".equals(type) || "次日达".equals(type))
            return true;
        return false;
    }

    /**
     * 根据长宽高，计算是否超长，并返回对应件数
     *
     * @param l 物品长度
     * @param w 物品宽度
     * @param h 物品高度
     * @return 物品件数
     */
    public static int isOverLong(double l, double w, double h, double num) {
        if (l <= 0 || w <= 0 || h <= 0 || num <= 0)
            return 0;
        if (l >= OVER_LONG_LIMIT || w >= OVER_LONG_LIMIT || h >= OVER_LONG_LIMIT)
            return (int) num;
        return 0;
    }

    /**
     * 从String转换为double，并预留以为小数
     */
    public static String format(String value) {
        if (TextUtils.isEmpty(value))
            return "0";
        return new BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 并预留以为小数
     */
    public static double format(double value) {
        return new BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 并预留以为小数
     */
    public static String formatDouble(double value) {
        return new BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 从String转换为double，并预留以为小数
     */
    public static double getValue(String value) {
        if (TextUtils.isEmpty(value))
            return 0.0;
        return new BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 从String转换为int
     */
    public static int stringToInt(String value) {
        if (TextUtils.isEmpty(value))
            return 0;
        return Integer.valueOf(value);
    }

    /**
     * 判断字符串是否为空，并返回对于数值
     */
    public static String stringFormat(String value) {
        if (TextUtils.isEmpty(value))
            return "0";
        return value;
    }


    /**
     * 判断当已输入超重重量时，超重件数是否为空
     */
    public static boolean isNumberNull(String weight, String number) {
        if (TextUtils.isEmpty(weight))
            return false;
        if (Double.valueOf(weight) > 0 && (TextUtils.isEmpty(number) || Double.valueOf(number) <= 0))
            return true;
        return false;
    }

    /**
     * 计算运费模块
     * 根据服务方式获取所属下标
     *
     * @param serviceMethod 服务方式-String
     * @return 服务方式-int
     */
    public static int getCalServiceType(String serviceMethod, String[] serviceConstants) {
        List<String> list = Arrays.asList(serviceConstants);
        if (list.contains(serviceMethod))
            return list.indexOf(serviceMethod);
        return 0;
    }

    /**
     * 获取服务方式上传类型
     *
     * @param serviceMethod，
     * @return
     */
    public static int calculatePos(String serviceMethod, String className) {
        if ("当天达".equals(serviceMethod)) { //当天达
            return 1;
        } else if ("次日达".equals(serviceMethod)) { //次日达
            return 2;
        } else if ("隔日达".equals(serviceMethod)) { //隔日达
            return 3;
        } else if ("陆运件".equals(serviceMethod)) { //陆运件
            return 4;
        } else if ("同城次日".equals(serviceMethod)) { //同城次日
            return 5;
        } else if ("同城即日".equals(serviceMethod)) { //同城即日
            return 10;
        } else if ("早班件".equals(serviceMethod)) { //早班件
            return 6;
        } else if ("中班件".equals(serviceMethod)) { //中班件
            return 7;
        } else if ("晚班件".equals(serviceMethod)) { //晚班件
            return 8;
        } else if ("次晨达".equals(serviceMethod)) { //次晨达
            return 9;
        } else if ("次晚达".equals(serviceMethod)) { //次晚达"
            return 99;
        } else if ("航空件".equals(serviceMethod)) { //航空件
            return 11;
        } /* else if (Constants.SERVICES[13].equals(serviceMethod)) {   // 冷运件
            return 12;
        }*/ else if ("省内即日".equals(serviceMethod)) { //省内即日
            return 13;
        } else if ("省内次日".equals(serviceMethod)) { //省内次日
            return 14;
        } else if ("空运".equals(serviceMethod)) {   // 空运
            return 15;
        } else if ("专运".equals(serviceMethod)) { //专运
            return 16;
        } /* else if (Constants.SERVICES[17].equals(serviceMethod)) {//b2b
            return 17;
        }*/
        return 0;
    }


    /*新ERP付款方式判断*/
    /**
     * 判断指定运单付款方式类型是否是寄方付（老ERP为寄付）
     * @return
     */
    public static boolean waybillPaymentIsSender(String paymentMethod){
        if (TextUtils.isEmpty(paymentMethod)){
            return false;
        }
        if (paymentMethod.contains("寄付") || paymentMethod.contains("寄方付" )){
            return true;
        }
        return false;
    }

    /**
     * 判断指定运单付款方式类型是否是到方付（老ERP为到付）
     * @return
     */
    public static boolean waybillPaymentIsrRceive(String paymentMethod){
        if (TextUtils.isEmpty(paymentMethod)){
            return false;
        }
        if (paymentMethod.contains("到付") || paymentMethod.contains("收方付" )){
            return true;
        }
        return false;
    }

    /**
     * 判断指付款方式类型是否是现结（老ERP为寄付）
     * @return
     */
    public static boolean paymentMethodIsCashSettlement(String paymentMethod){
        if (TextUtils.isEmpty(paymentMethod)){
            return false;
        }
        if (paymentMethod.contains("寄付") || paymentMethod.contains("现结" )){
            return true;
        }
        return false;
    }

    /**
     * 判断指付款方式类型是否是非现结（老ERP的月结，因为老ERP除了寄付就是月结，所以新ERP非现结就不是寄付且不是现结）
     * @return
     */
    public static boolean paymentMethodNoCashSettlement(String paymentMethod){
        if (TextUtils.isEmpty(paymentMethod)){
            return false;
        }
        if (!paymentMethod.contains("现结") && !paymentMethod.contains("寄付")){
            return true;
        }
        return false;
    }
}
