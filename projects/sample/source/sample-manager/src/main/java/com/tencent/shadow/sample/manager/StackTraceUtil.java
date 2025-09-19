package com.tencent.shadow.sample.manager;

/**
 * @author lgj
 * @description:
 * @date : 2025/9/19 16:42
 */
import android.util.Log;

public class StackTraceUtil {
    private static final String TAG = "StackTraceUtil";

    /**
     * 打印当前方法调用栈信息
     */
    public static void printStackTrace() {
        printStackTrace(TAG);
    }

    /**
     * 打印当前方法调用栈信息
     * @param tag 日志标签
     */
    public static void printStackTrace(String tag) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append("Method call stack:\n");

        // 从第3个元素开始，跳过getStackTrace和printStackTrace方法本身
        for (int i = 3; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            sb.append("  at ")
                    .append(element.getClassName())
                    .append(".")
                    .append(element.getMethodName())
                    .append("(")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")\n");
        }

        Log.d(tag, sb.toString());
    }

    /**
     * 获取当前方法调用栈信息字符串
     * @return 调用栈信息字符串
     */
    public static String getStackTraceString() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append("Method call stack:\n");

        // 从第3个元素开始，跳过getStackTrace和getStackTraceString方法本身
        for (int i = 3; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            sb.append("  at ")
                    .append(element.getClassName())
                    .append(".")
                    .append(element.getMethodName())
                    .append("(")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")\n");
        }

        return sb.toString();
    }

    /**
     * 获取当前方法名
     * @return 当前方法名
     */
    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    /**
     * 获取调用当前方法的方法名
     * @return 调用者方法名
     */
    public static String getCallerMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    /**
     * 获取当前类名
     * @return 当前类名
     */
    public static String getCurrentClassName() {
        return Thread.currentThread().getStackTrace()[3].getClassName();
    }
}
