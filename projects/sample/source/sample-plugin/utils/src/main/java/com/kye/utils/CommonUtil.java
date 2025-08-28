package com.kye.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Routing
 * Desc 常用的公共类
 * Source
 * Created by yb on 2018/10/25 15:06
 * Modify by yb on 2018/10/25 15:06
 * Version 1.0
 */
public class CommonUtil {

    private CommonUtil() {
    }

    /**
     * 拨打电话-调出拨打界面
     *
     * @param context 上下文
     * @param phone   电话号码
     */
    public static void call(Context context, String phone) {
        //Intent intent = new Intent(Intent.ACTION_CALL);
        //不直接拨打
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        context.startActivity(intent);
    }


    public static boolean ishasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                // 没有SIM卡
                result = false;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取尺寸像素值
     *
     * @param context 上下文
     * @param resId   资源id
     * @return 默认值
     */
    public static int getDimenInt(Context context, @DimenRes int resId) {
        return context.getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 根据颜色资源id获取颜色
     *
     * @param context 上下文
     * @param id      资源ID
     * @return 默认值
     */
    public static int getColorResource(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * 根据String资源id获取颜色
     *
     * @param context 上下文
     * @param id      资源ID
     * @return 默认值
     */
    public static String getStringResource(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 获取指定时间格式的字符串
     *
     * @param date 默认值
     * @return 默认值
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     *
     * @param number 待检验的字符串
     * @return 返回是否包含
     */
    public static boolean judgeContainsStr(String number) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(number);
        return m.matches();
    }

    public static void copyBigDataToSD(Context context, String fileAssetPath, String strOutFileName) throws IOException {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = context.getAssets().open(fileAssetPath);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }

    public static void stringTxt(String str, String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath);//SD卡中的路径
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取随机外请ID
     *
     * @return 默认值
     */
    public static String getRandomOuterID() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int length = characters.length();
        int index = new Random().nextInt(length);
        char ch = characters.charAt(index);
        StringBuilder builder = new StringBuilder();
        builder.append("W");
        builder.append(ch);
        String currentTimeStr = String.valueOf(System.currentTimeMillis());
        String number = currentTimeStr.substring(currentTimeStr.length() - 4);
        builder.append(number);
        return builder.toString();
    }

    public static int getServiceImageIDByName(Context context, String serviceName) {
        int id = -1;
        HashMap<String, String> map = new HashMap<>();
        map.put("内部急件", "ic_service1");
        map.put("次晨达", "ic_service2");
        map.put("次日达", "ic_service3");
        map.put("空运", "ic_service4");
        map.put("隔日达", "ic_service5");
        if (map.containsKey(serviceName)) {
            String imageName = map.get(serviceName);
            id = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        }
        return id;
    }

    public static int getServiceImageIDByCode(Context context, String serviceCode) {
        int id = -1;
        HashMap<String, String> map = new HashMap<>();
        map.put("内部急件", "ic_service1");
        map.put("内部急件", "ic_service2");
        map.put("内部急件", "ic_service3");
        map.put("内部急件", "ic_service4");
        map.put("内部急件", "ic_service5");
        if (map.containsKey(serviceCode)) {
            String imageName = map.get(serviceCode);
            id = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        }
        return id;
    }


    public static class PhoneBroadcastReceiver extends BroadcastReceiver {

        private static final String TAG = "PhoneStatReceiver";
        //        private static MyPhoneStateListener phoneListener = new MyPhoneStateListener();
        private static boolean incomingFlag = false;
        private static String incoming_number = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            //如果是拨打电话
            if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
                incomingFlag = false;
                String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.i(TAG, "call OUT:" + phoneNumber);
            } else {
                //如果是来电
                TelephonyManager tm =
                        (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

                switch (tm.getCallState()) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        incomingFlag = true;//标识当前是来电
                        incoming_number = intent.getStringExtra("incoming_number");
                        Log.i(TAG, "RINGING :" + incoming_number);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        if (incomingFlag) {
                            Log.i(TAG, "incoming ACCEPT :" + incoming_number);
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (incomingFlag) {
                            Log.i(TAG, "incoming IDLE");
                        }
                        break;
                }
            }
        }
    }

    // 手机号码验证
    public static final String PHONE_TEL = "^1\\d{10}$";


    /**
     * 验证是否为手机号
     *
     * @param number 号码
     * @return true 是
     */
    public static boolean isPhoneTel(String number) {
        if (!TextUtils.isEmpty(number)) {
            return number.matches(PHONE_TEL);
        }
        return false;
    }

    /**
     * 判断输入的经纬度是否合法
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 默认值
     */
    public static boolean isValidCoordinate(String longitude, String latitude) {
        try {
            return isValidCoordinate(Double.parseDouble(longitude), Double.parseDouble(latitude));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断输入的经纬度是否合法(注意：这里经纬度为0时会被判断为不合法)
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 默认值
     */
    public static boolean isValidCoordinate(double longitude, double latitude) {
        //经度最大是180° 最小是0°
        if (0.0 >= longitude || 180.0 < longitude) {
            return false;
        }
        //纬度最大是90° 最小是0°
        if (0.0 >= latitude || 90.0 < latitude) {
            return false;
        }
        return true;
    }

    /**
     * 获取随机6位数字加字母
     */
    public static String getRandomCode() {
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int rand = (int) (Math.random() * a.length());
            builder.append(a.charAt(rand));
        }
        return builder.toString();
    }
//    public static BroadcastReceiver registerVolumeReceiver(Context context, Callback callback) {
//        PhoneBroadcastReceiver mVolumeReceiver = null;
//        try {
//            mVolumeReceiver = new PhoneBroadcastReceiver(callback);
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("android.media.VOLUME_CHANGED_ACTION");
//            context.registerReceiver(mVolumeReceiver, filter);
//        } catch (IllegalArgumentException ex1) {
//            ex1.printStackTrace();
//        } catch (Exception ex2) {
//            ex2.printStackTrace();
//        }
//        return mVolumeReceiver;
//    }
//
//    public static void unRegisterVolumeReceiver(Context context, BroadcastReceiver receiver) {
//        try {
//            if (context != null && receiver != null) {
//                context.unregisterReceiver(receiver);
//            }
//        } catch (IllegalArgumentException ex1) {
//            ex1.printStackTrace();
//        } catch (Exception ex2) {
//            ex2.printStackTrace();
//        }
//    }
}
