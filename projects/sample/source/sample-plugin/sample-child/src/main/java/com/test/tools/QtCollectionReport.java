package com.test.tools;


import android.util.Log;

import com.kye.pda.burypoint.QuickTrackingUtil;
import com.kye.pda.burypoint.constants.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author lgj
 * @description: Qt埋点上报（Java 版本）
 * @date : 2024/4/18 10:38
 */
public class QtCollectionReport {

    private QtCollectionReport() {
        // 工具类禁止实例化
    }

    /**
     * 点击埋点
     *
     * @param btnName    按钮名称
     * @param moduleName 模块名称
     */
    public static void putClickEvent(String btnName, String moduleName) {
        Log.d("lgj", "点击埋点：" + btnName + " 模块：" + moduleName);
        QuickTrackingUtil.getInstance()
                .putEventMap(Constants.BUTTON_NAME, btnName)
                .putEventMap(Constants.MODULE_NAME, moduleName)
                .onEventObject(Constants.CLK_EVENT);
    }

    /**
     * 应用文件下载时间上报
     *
     * @param downloadFileType    下载文件类型
     * @param downloadFileSize    下载文件大小，单位：Byte
     * @param downloadDuration    下载耗时时长，单位：ms
     * @param downloadResult      下载结果。成功；失败
     * @param downloadFailedReason 下载失败原因
     */
    public static void downloadTimeEventReport(String downloadFileType,
                                               long downloadFileSize,
                                               long downloadDuration,
                                               String downloadResult,
                                               String downloadFailedReason) {
        // 文件大小，M
        BigDecimal sizeFinalM = toDevNumber(downloadFileSize, 1048576L, 3);
        // 下载耗时，秒
        BigDecimal spaceTimes = toDevNumber(downloadDuration, 1000L, 3);
        if (spaceTimes.longValue() <= 0L) {
            return;
        }
        String sizeFinalStr = sizeFinalM.toString();
        String spaceTimesStr = spaceTimes.toString();
        // 文件大小，kb
        BigDecimal sizeFinalKb = toDevNumber(downloadFileSize, 1024L, 3);
        // 下载速度 kb/秒
        String downloadSpeedStr = calcDownloadSpeed(sizeFinalKb.doubleValue(), spaceTimes.doubleValue());

        QuickTrackingUtil.getInstance()
                .putEventMap(Constants.DOWNLOAD_FILE_TYPE, downloadFileType)
                .putEventMap(Constants.DOWNLOAD_FILE_SIZE, sizeFinalStr)
                .putEventMap(Constants.DOWNLOAD_DURATION, spaceTimesStr)
                .putEventMap(Constants.DOWNLOAD_SPEED, downloadSpeedStr)
                .putEventMap(Constants.DOWNLOAD_RESULT, downloadResult)
                .putEventMap(Constants.DOWNLOAD_FAILED_REASON, downloadFailedReason)
                .onEventObject(Constants.OTHER_DOWNLOAD_OPERATION_TIME);
    }

    /**
     * 插件安装或启动时间上报
     *
     * @param otherInstallStartOperationType 埋点操作类型
     * @param installStartPluginType 插件类型
     * @param operateDuration 耗时时长，单位：ms
     * @param operatePluginNum 插件个数
     * @param operateResult 操作结果
     * @param operateFailedReason 操作失败原因
     * @param operateRemark 其他备注
     */
    public static void installOrStartTimeEventReport(String otherInstallStartOperationType,
                                                     String installStartPluginType,
                                                     long operateDuration,
                                                     int operatePluginNum,
                                                     String operateResult,
                                                     String operateFailedReason,
                                                     String operateRemark) {
        // 耗时，秒
        BigDecimal spaceTimes = toDevNumber(operateDuration, 1000L, 3);
        if (spaceTimes.longValue() <= 0L) {
            return;
        }
        String spaceTimesStr = spaceTimes.toString();
        String pluginNumStr = String.valueOf(operatePluginNum);
        String failedReason = (operateFailedReason == null || operateFailedReason.isEmpty())
                ? "" : operateFailedReason;

        QuickTrackingUtil.getInstance()
                .putEventMap(Constants.OTHER_INSTALL_START_OPERATION_TYPE, otherInstallStartOperationType)
                .putEventMap(Constants.INSTALL_START_PLUGIN_TYPE, installStartPluginType)
                .putEventMap(Constants.INSTALL_START_PLUGIN_NUM, pluginNumStr)
                .putEventMap(Constants.INSTALL_START_DURATION, spaceTimesStr)
                .putEventMap(Constants.INSTALL_START_PLUGIN_RESULT, operateResult)
                .putEventMap(Constants.INSTALL_START_PLUGIN_FAILED_REASON, failedReason)
                .putEventMap(Constants.INSTALL_START_PLUGIN_REMARK, operateRemark)
                .onEventObject(Constants.OTHER_INSTALL_START_OPERATION_TIME);
    }

    /**
     * 将数值转换为指定的单位数值
     *
     * @param number 数值
     * @param devNumber 要除的数值
     * @param keepPointCount 保留小数点位数
     * @return BigDecimal
     */
    private static BigDecimal toDevNumber(long number, long devNumber, int keepPointCount) {
        if (devNumber <= 0) {
            return BigDecimal.ZERO;
        }
        try {
            BigDecimal spaceBig = new BigDecimal(number);
            BigDecimal oneT = new BigDecimal(devNumber);
            BigDecimal spaceTimeS = spaceBig.divide(oneT, keepPointCount, RoundingMode.HALF_UP);
            return spaceTimeS;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 计算下载平均速度
     *
     * @param fileSize 文件大小
     * @param spaceTime 耗时
     * @return 速度字符串
     */
    private static String calcDownloadSpeed(double fileSize, double spaceTime) {
        if (spaceTime <= 0.0) {
            return "0.0";
        }
        double speed = fileSize / spaceTime;
        return String.format("%.2f", speed);
    }
}
