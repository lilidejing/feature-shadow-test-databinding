package com.kye.utils

import android.text.TextUtils

/**
 * @author 刘涛13
 * @description: 验证同一运单重复扫描
 * @date : 2024/8/23
 */
class ScanRepetitionUtil {
    // 扫描间隔时间
    private val TIME_INTERVAL: Long = 1000

    // 扫描单号
    private var scanNumber: String? = null

    // 扫描时间
    private var scanTime: Long = 0

    /**
     * 验证是否重复扫描
     * @param result 扫描结果
     * @return true:重复扫描 false:非重复扫描
     */
    fun isRepetition(result: String?): Boolean {
        if (!TextUtils.isEmpty(result)) {
            val currentTimeMillis = System.currentTimeMillis()
            if (result == scanNumber) {
                if (currentTimeMillis - scanTime < TIME_INTERVAL) {
                    return true
                } else {
                    scanTime = currentTimeMillis
                }
            } else {
                scanNumber = result
                scanTime = currentTimeMillis
            }
        }
        return false
    }
}