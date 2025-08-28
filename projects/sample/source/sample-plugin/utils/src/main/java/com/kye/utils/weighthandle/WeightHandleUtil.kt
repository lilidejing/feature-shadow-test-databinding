package com.kye.utils.weighthandle

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.kye.pda.biz.common.utils.R
//import com.kye.pda.utils.SpannableStringUtil
import java.math.BigDecimal

/**
 *@author lgj
 *@description: 处理重量工具类
 *@date : 2024/6/14 16:02
 */
class WeightHandleUtil {
    companion object {


        /**
         * 获取重量显示
         * @param weight 输入重量
         */
        fun getWeightDisplay(weight: String?): String {
            if (weight.isNullOrEmpty()) {
                return weight ?: ""
            }
            val weightD = try {
                weight.toDouble()
            } catch (e: Exception) {
                0.0
            }
            if (weightD >= 1000) {
                val donUnit = strToBigDecimal("${weightD.div(1000.0)}").toPlainString() + "吨"
                return "$weight 公斤 $donUnit"
            } else if (weightD > 0) {
                return "$weight 公斤"
            }
            return weight
        }

        /**
         * 输入框处理重量展示方法
         * @param weight 重量字符串
         */
       /* fun handleWeightDisplay(context: Context?, weight: String?): CharSequence? {
            if (context == null || weight.isNullOrEmpty()) {
                return weight
            }
            if (weight.contains("吨")) {
                val spannableString = SpannableString(weight)
                val startKg = weight.indexOf("公斤")
                if (startKg != -1) {
                    val colorSpan =
                        ForegroundColorSpan(ContextCompat.getColor(context,R.color.common_color_functional_black_no4))
                    spannableString.setSpan(
                        colorSpan,
                        startKg,
                        startKg + "公斤".length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    val donStr = weight.substring(startKg + 2).trim()
                    val startDon = weight.indexOf(donStr)
                    val endDon = weight.indexOf("吨")
                    if (startDon < endDon) {
                        val bgColor =
                            ContextCompat.getColor(
                                context,
                                R.color.common_color_functional_gray_no3,
                            )
                        val fillColor =
                            ContextCompat.getColor(
                                context,
                                R.color.common_color_functional_gray_no2,
                            )
                        val textColor =
                            ContextCompat.getColor(
                                context,
                                R.color.common_color_functional_black_no6,
                            )
                        val radius =
                            context.resources.getDimension(
                                R.dimen.dp_8
                            )
                        val strokeWidth =
                            context.resources.getDimension(
                                R.dimen.dp_1
                            )
                        val horizontalPadding =
                            context.resources.getDimension(
                                R.dimen.dp_1
                            )
                        val verticalPadding =
                            context.resources.getDimension(
                                R.dimen.dp_7
                            )
                        val bgSpan =
                            RoundBackgroundColorSpan(bgColor, fillColor, textColor, radius.toInt())
                        bgSpan.setStrokeWidth(strokeWidth)
                        bgSpan.setHorizontalPadding(horizontalPadding)
                        bgSpan.setVerticalPadding(verticalPadding)
                        bgSpan.setmTextSize(
                            context.resources.getDimension(
                                R.dimen.dp_18
                            )
                        )

                        val horizontalFillPadding =
                            context.resources.getDimension(
                                R.dimen.dp_1
                            )
                        val verticalFillPadding =
                            context.resources.getDimension(
                                R.dimen.dp_7
                            )
                        bgSpan.setHorizontalFillPadding(horizontalFillPadding)
                        bgSpan.setVerticalFillPadding(verticalFillPadding)
                        spannableString.setSpan(
                            bgSpan,
                            startDon,
                            endDon + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    return spannableString
                }
            } else {
                return SpannableStringUtil.getTextByColor(
                    context,
                    weight,
                    "公斤",
                    R.color.common_color_functional_black_no4
                )

            }
            return weight

        }
*/
        /**
         * 字符串转BigDecimal
         *
         * @param str
         * @return boolean
         */
        private fun strToBigDecimal(str: String): BigDecimal {
            var bd = BigDecimal(str).stripTrailingZeros()
            // 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
            return bd
        }

    }
}