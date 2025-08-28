package com.kye.utils

import android.text.TextUtils
//import com.kye.pda.utilcode.util.ActivityUtils

/**
 * @author gsy
 * Description:换单流水工具类
 * @date : 2024/6/14
 */
object ChangeOrderFlowUtil {

    /**
     * 换单流水跟版本号
     */
    private const val HD_LS = "HDV1"

    /**
     * 换单标识
     */
    private const val HD_TAG = "HD"

    /**
     * KYE转换后内容LSH
     */
    private const val LSH_TAG = "LSH"

    /**
     * KY转换后内容LS
     */
    private const val LS_TAG = "LS"

    /**
     * A转换后子件分割符-
     */
    private const val CHILD_TAG = "A"
    private const val KYE_TAG = "KYE"
    private const val KY_TAG = "KY"

    /**
     * 母单号类型 格式：MD2407-416809
     */
    private const val MD_TYPE = "MD"
    private const val CHILD_SPLIT_TAG = "-"

    /**
     * 判断是否是换单流水号
     *
     * @param waybillNumber 换单流水号
     * @return true 是 false 否
     */
    fun isChangeOrderFlowNumber(waybillNumber: String?): Boolean {
        return waybillNumber?.startsWith(HD_TAG) == true
    }

    /**
     * 是否是母单号
     */
    fun isParentNumber(scanNumber: String?): Boolean {
        return scanNumber?.contains(MD_TYPE) == true
    }

    /**
     * 规则转换数值减1
     * @param result 运单号内容
     * @return 转换后内容
     */
    private fun charConvert(result: String): String {
        // 规则转换
        val charArray = result.toCharArray()
        val stringBuffer = StringBuffer()
        var char: Char
        for (c in charArray) {
            char = c
            // 判断是否是数字
            if (Character.isDigit(char)) {
                char = if (char.code == 48) {
                    57.toChar()
                } else {
                    (char.code - 1).toChar()
                }
            }
            stringBuffer.append(char)
        }
        return stringBuffer.toString()
    }

    /**
     * 换单流水号转换成跨越运单
     *
     * @param scanNumber 扫描单号
     * @return 跨越运单号
     */
    /*fun serialNumberConvertWaybillNumber(scanNumber: String?): String {
        if (TextUtils.isEmpty(scanNumber)) {
            return ""
        }
        // 去掉HDV1标识 HD(换单) V1(版本)
        val group = scanNumber?.split(HD_LS.toRegex())?.dropLastWhile { it.isEmpty() }
            ?.toTypedArray()
        // 运单号处理
        return if (group?.size != null && group.size > 1) {
            if (isParentNumber(scanNumber)) {
                // 规则转换
                charConvert(group[1])
            } else {
                val tempNumber = group[1].replace(LSH_TAG, KYE_TAG).replace(LS_TAG, KY_TAG)
                var childNumber = ""
                var waybillNumber = tempNumber
                // 是否是子件
                if (tempNumber.contains(CHILD_TAG)) {
                    // 运单号
                    waybillNumber = tempNumber.substring(0, tempNumber.indexOf(CHILD_TAG))
                    // 子件
                    childNumber = tempNumber.substring(tempNumber.indexOf(CHILD_TAG))
                        .replace(CHILD_TAG, CHILD_SPLIT_TAG)
                }
                // 规则转换
                val stringBuffer = charConvert(waybillNumber)
                SecurityCodeUtil.filterSecurityCode(
                    ActivityUtils.getTopActivity()?.hashCode(),
                    stringBuffer + childNumber
                )
            }
        } else {
            scanNumber ?: ""
        }
    }*/
}