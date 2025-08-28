package com.kye.utils

/**
@author BradySun
@description:签收人内容合法性校验
@date : 2025/8/1 16:31
 */
object SignPersonFormatVerifyUtil {
    /**
     * 签收人内容格式合法性校验
     */
    fun verifyFormat(content: String?): String {
        if (content.isNullOrEmpty()) {
            return ""
        }
        //签收人姓名仅支持【-】【,】【·】【空格】特殊符号，若输入其他特殊符号
        val regex = "^[\\u4e00-\\u9fa5a-zA-Z0-9\\s\\-,·]+$".toRegex()
        if (!regex.matches(content)) {
            return "签收人姓名不能包含特殊符号"
        } else {
            //校验是否至少包含中文或英文

            // 是否包含中文
            val hasChinese = content.any { it in '\u4e00'..'\u9fa5' }
            // 是否包含英文
            val hasEnglish = content.any { it.isLetter() && it.isLowerCase() || it.isUpperCase() }
            return if (!hasChinese && !hasEnglish) {
                "签收人姓名必须包含中文或英文"
            } else {
                ""
            }
        }
    }
}