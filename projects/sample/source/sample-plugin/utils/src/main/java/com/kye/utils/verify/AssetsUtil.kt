package com.kye.utils.verify

import java.util.regex.Pattern

/**
 *@author lgj
 *@description:判断流动资产类型
 *@date : 2023/9/10 11:26
 */

/**
 * 是否是卡板
 * @return true，是；false，不是
 */
fun String?.isCardBoard():Boolean{
    if (this == null){
        return false
    }
    val regex = "LDZC-01\\d{2}\\d{6}"
    val pattern: Pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * 是否是周转箱
 * @return true，是；false，不是
 */
fun String?.isCircleBox():Boolean{
    if (this == null){
        return false
    }
    val regex = "LDZC-02\\d{2}\\d{6}"
    val pattern: Pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}


/**
 * 是否是垫板
 * @return true，是；false，不是
 */
fun String?.isBottomBoard():Boolean{
    if (this == null){
        return false
    }
    val regex = "LDZC-03\\d{2}\\d{6}"
    val pattern: Pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}