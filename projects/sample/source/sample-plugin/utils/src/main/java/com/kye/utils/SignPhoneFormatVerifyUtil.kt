package com.kye.utils

import com.kye.utils.verify.BarcodeVerifyUtils

/**
@author BradySun
@description:签收手机格式校验
@date : 2025/8/5 15:33
 */
object SignPhoneFormatVerifyUtil {
    /**
     * 是否加密
     */
    fun isEncrypt(phoneNumber: String?): Boolean {
        if (phoneNumber.isNullOrEmpty()) {
            return false
        }
        return phoneNumber.contains("*")
    }

    /**
     * 是否是手机号
     */
    fun isMobilePhone(phoneNumber: String?):Boolean{
        if (phoneNumber.isNullOrEmpty()) {
            return false
        }
       return  BarcodeVerifyUtils.isMobilePhoneNumber(phoneNumber)
    }
}