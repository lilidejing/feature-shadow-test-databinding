package com.kye.utils;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/12.
 * 关键字变色或变大小
 */

public class TextKeywordUtil {
    private static final String TAG = "TextKeywordUtil";

    /**
     * 单个关键字高亮变色
     *
     * @param color   变化的色值
     * @param string  文字
     * @param keyWord 文字中的关键字
     * @return
     */
    public static CharSequence KeywordChangeColorSingle(int color, String string, String keyWord) {
        SpannableStringBuilder builder = new SpannableStringBuilder(string);
        if (TextUtils.isEmpty(keyWord)) {
            return builder;
        }
        int indexOf = string.indexOf(keyWord);
        if (indexOf != -1) {
            builder.setSpan(new ForegroundColorSpan(color), indexOf, indexOf + keyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    /**
     * 单个关键字改变大小和颜色
     *
     * @param textSize 文字的大小
     * @param string   文字
     * @param keyWord  文字中的关键字
     * @return
     */
    public static CharSequence keywordChangeScaleAndColor(int textSize, int textColor, String string, String keyWord) {
        SpannableString builder = new SpannableString(string);
        if (TextUtils.isEmpty(keyWord)) {
            return builder;
        }
        int indexOf = string.indexOf(keyWord);
        if (indexOf != -1) {
            builder.setSpan(new AbsoluteSizeSpan(textSize, true), indexOf, indexOf + keyWord.length()
                    , Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            Pattern p = Pattern.compile(keyWord);
            Matcher m = p.matcher(builder);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                builder.setSpan(new ForegroundColorSpan(textColor), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(new StyleSpan(Typeface.NORMAL), start, end, 0);

            }
        }
        return builder;
    }


    /**
     * 单个关键字改变大小
     *
     * @param textSize 文字的大小
     * @param string   文字
     * @param keyWord  文字中的关键字
     * @return
     */
    public static CharSequence KeywordChangeScaleSingle(int textSize, String string, String keyWord) {
        SpannableString builder = new SpannableString(string);
        if (TextUtils.isEmpty(keyWord)) {
            return builder;
        }
        int indexOf = string.indexOf(keyWord);
        if (indexOf != -1) {
            builder.setSpan(new AbsoluteSizeSpan(textSize, true), indexOf, indexOf + keyWord.length()
                    , Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return builder;
    }

    /**
     * 关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return
     */
    public static CharSequence KeywordChangeNewColor(int color, String text,
                                                     String keyword) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (TextUtils.isEmpty(keyword)) {
            return builder;
        }
        String splitText = text;
        while (splitText.contains(keyword)) {
            int start = splitText.indexOf(keyword);
            int end = start + keyword.length();
            builder.setSpan(new ForegroundColorSpan(color), text.indexOf(splitText) + start
                    , text.indexOf(splitText) + end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            splitText = splitText.substring(end, splitText.length());
        }
        return builder;
    }

    /**
     * 关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return
     */
    public static CharSequence KeywordChangeColor(int color, String text,
                                                  String keyword) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (TextUtils.isEmpty(keyword)) {
            return builder;
        }
        //keyword搜索关键字可能出现特殊字符如（类，需要转成普通字符
        String pattern = Pattern.quote(keyword);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(builder);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            builder.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    /**
     * 多个关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字数组
     * @return
     */
    public static CharSequence KeywordChangeColor(int color, String text,
                                                  String[] keyword) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(builder);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                builder.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

}
