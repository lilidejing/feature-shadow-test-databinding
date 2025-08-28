package com.kye.utils

import android.text.TextUtils
import com.kye.pda.utilcode.util.ActivityUtils
import com.kye.utils.verify.BarcodeVerifyUtils
import java.util.concurrent.ConcurrentHashMap

/**
 * @author gsy
 * Description:防伪码工具类
 * @date : 2024/6/14
 */
object SecurityCodeUtil {
    /**
     * key当前界面hashCode，value 扫描的内容
     */
    private val mActivityNameMap = HashMap<Int, ConcurrentHashMap<String, String>>()

    /**
     * 分隔符
     */
    private const val SPLIT = "|"

    /**
     * 过滤防伪码
     */
    fun filterSecurityCode(activityHashCode: Int?, result: String?): String {
        if (result?.contains(SPLIT) == true) {
            val args = result.split(SPLIT)
            if (BarcodeVerifyUtils.isAllWaybill(args[0]) && !TextUtils.isEmpty(args[1])) {
                disposeSecurityCode(activityHashCode, args)
                return args[0]
            }
        }
        return result ?: ""
    }

    /**
     * 处理防伪码逻辑
     */
    private fun disposeSecurityCode(activityHashCode: Int?, args: List<String>) {
        if (BarcodeVerifyUtils.getWaybillNumber(args[0]).length == 15) {
            val split = args[0].split("[*-]".toRegex())
            var securityCode = args[1]
            if (split.isNotEmpty() && securityCode.length == 1) {
                val last = split.last()
                securityCode += last
            }
            saveSecurityCode(activityHashCode, args[0], securityCode)
        } else {
            saveSecurityCode(activityHashCode, args[0], args[1])
        }
    }

    /**
     * 保存防伪码
     */
    private fun saveSecurityCode(activityHashCode: Int?, key: String, value: String) {
        activityHashCode?.let {
            if (mActivityNameMap[activityHashCode] == null) {
                mActivityNameMap[activityHashCode] = ConcurrentHashMap()
            }
            mActivityNameMap[activityHashCode]?.put(key, value)
        }
    }

    /**
     * 查询防伪码
     */
    fun getSecurityCode(key: String): String? {
        val topActivity = ActivityUtils.getTopActivity() ?: return ""
        return mActivityNameMap[topActivity.hashCode()]?.get(key)
    }

    /**
     * 清空防伪码
     */
    fun clearSecurityCode(activityHashCode: Int) {
        mActivityNameMap[activityHashCode]?.clear()
    }
}