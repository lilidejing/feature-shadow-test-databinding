package com.kye.utils;

import android.content.Context;

import com.getkeepsafe.relinker.ReLinker;
import com.kye.pda.utilcode.util.Logger;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

public class MMKVUtil {
    private static final String ID = "PDA";

    public static void initMMKV(Context context) {
        String dir = FileUtils.getMMKVFolderPath();
        String rootDir = MMKV.initialize(context, dir, libName -> {
                    ReLinker.log(message -> {
//                                TrackingLogUtils.INSTANCE.trackLogToLocal(
//                                        context,
//                                        "【kyRelinker】" + message,
//                                        LogFillUtil.TYPE_BURY
//                                );
                            })
                            .force() // 如果用到了relinker的加载方式，走强制更新，确保用的都是插件自己的so版本
                            .loadLibrary(context, libName);
                }
                , MMKVLogLevel.LevelError
                , new MMKVHandler() {
                    @Override
                    public MMKVRecoverStrategic onMMKVCRCCheckFail(String mmapID) {
                        return null;
                    }

                    @Override
                    public MMKVRecoverStrategic onMMKVFileLengthError(String mmapID) {
                        return null;
                    }

                    @Override
                    public boolean wantLogRedirecting() {
                        return false;
                    }

                    @Override
                    public void mmkvLog(MMKVLogLevel level, String file, int line, String function, String message) {
//                        TrackingLogUtils.INSTANCE.trackLogToLocal(
//                                context,
//                                "【kyMMKV】" + message,
//                                LogFillUtil.TYPE_BURY
//                        );
                    }
                }
        );
        Logger.i("MMKV地址" + rootDir);
    }

    public static boolean putString(String key, String content) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.encode(key, content);
    }

    public static boolean putInt(String key, int content) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.encode(key, content);
    }

    public static boolean putDouble(String key, double content) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.encode(key, content);
    }

    public static boolean putBoolean(String key, boolean content) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.encode(key, content);
    }

    public static String getString(String key, String defaultValue) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.decodeString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.decodeInt(key, defaultValue);
    }

    public static double getDouble(String key, double defaultValue) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.decodeDouble(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        return mmkv.decodeBool(key, defaultValue);
    }

    public static void removeString(String key) {
        MMKV mmkv = MMKV.mmkvWithID(ID, MMKV.MULTI_PROCESS_MODE);
        mmkv.removeValueForKey(key);
    }
}
