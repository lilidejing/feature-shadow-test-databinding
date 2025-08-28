package com.kye.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kye.pda.utilcode.util.ThreadUtils;
import com.kye.utils.toast.KYToastUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Routing
 * Desc 文本工具类
 * Source
 * Created by Chase on 2018/12/3 16:34
 * Modify by Chase on 2018/12/3 16:34
 * Version 1.0
 */
public final class TextUtil {

    private static final String TAG = "TextUtil";

    public static final String DEFAULT_TEXT = "——";//默认文本

    public static final String INVITE_OUT_MARK = "(外请)";//外请标记

    private TextUtil() {
    }

    /**
     * 代码设置光标颜色
     *
     * @param editText 你使用的EditText
     * @param color    光标颜色
     */
    public static void setCursorDrawableColor(EditText editText, int color) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");//获取这个字段
            fCursorDrawableRes.setAccessible(true);//代表这个字段、方法等等可以被访问
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);

            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);

            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            Drawable[] drawables = new Drawable[2];
            drawables[0] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[1] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            if (drawables[0] != null) {
                drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);//SRC_IN 上下层都显示。下层居上显示。
            }
            if (drawables[1] != null) {
                drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable ignored) {
        }
    }

    /**
     * 设置 DINAlternate-Bold 字体
     */
    public static void setTypeface(Context context, TextView textView) {
        RxSchedulers.getExecutorService().execute(() -> {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/din_alternate_bold.ttf");
            setTypeface(textView, typeface);
        });

    }

    public static void setTypefaceOppoSans(TextView textView) {
        setTypefaceOppoSans(textView.getContext(), textView);
    }

    public static void setTypefaceOppoSans(Context context, TextView textView) {
        if (context == null) {
            return;
        }
        RxSchedulers.getExecutorService().execute(() -> {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/OPPOSans-H.ttf");
            setTypeface(textView, typeface);
        });
    }

    /**
     * 设置字体
     *
     * @param textView view
     * @param typeface 字体类型
     */
    private static void setTypeface(TextView textView, Typeface typeface) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setTypeface(typeface);
            }
        });
    }

    public static void setTypeFaceBold(TextView textView) {
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    /**
     * 设置多个颜色的 TextView
     */
    public static void setColorfulText(TextView tv, String[] contents, int[] colors) {
        if (tv == null || contents == null || colors == null || colors.length != contents.length) {
            throw new RuntimeException(TAG + " setColorfulText 异常");
        }
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < contents.length; i++) {
            sb.append(contents[i]);
        }
        SpannableStringBuilder style = new SpannableStringBuilder(sb);
        int begin = 0;
        for (int i = 0; i < contents.length; i++) {
            int end = begin + contents[i].length();
            style.setSpan(new ForegroundColorSpan(colors[i]), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            begin = end;
        }
        tv.setText(style);
    }


    /**
     * 设置文本，如果文本为空，则设置为默认文本
     */
    public static void setTextOrDefault(TextView tv, String s) {
        if (tv != null) {
            tv.setText(TextUtils.isEmpty(s) ? DEFAULT_TEXT : s);
        }
    }


    /**
     * 是否是浮点数（double和float）
     */
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        boolean isDouble = true;
        try {
            Double.valueOf(str);
        } catch (NumberFormatException e) {
            isDouble = false;
        } finally {
            return isDouble;
        }

    }

    /**
     * 是否是数字（判断单个字符）
     */
    public static boolean isNumber(String str) {
        return str.matches("\\d");
    }

    /**
     * 是否是数字字符串（判断的字符串）
     */
    public static boolean isNumStr(String str) {
        return str.matches("[0-9]+");
    }


    /**
     * 转换：List<Integer> -> int[]
     */
    public static int[] transferIntegerList2IntArray(List<Integer> integerList) {
        int[] intArray = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            intArray[i] = integerList.get(i);
        }
        return intArray;
    }

    /**
     * double转String
     *
     * @param decimalDigits 保留几位小数
     */
    public static String double2String(double d, int decimalDigits) {
        //同：String.format("%.2f", scanWeight)
        NumberFormat nf = NumberFormat.getInstance();
        //设置数的小数部分所允许的最大位数，避免小数位被舍掉
        nf.setMaximumFractionDigits(decimalDigits);
        //设置数的小数部分所允许的最小位数，避免小数位有多余的0
        nf.setMinimumFractionDigits(decimalDigits);
        //去掉科学计数法显示，避免显示为111,111,111,111
        nf.setGroupingUsed(false);
        return nf.format(d);
    }

    /**
     * double通过四舍五入保留几位小数
     */
    public static double getValidDouble(double d, int decimalDigits) {
        BigDecimal b = new BigDecimal(d);
        return b.setScale(decimalDigits, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取文本或者默认的文本
     */
    public static String getTextOrDefault(String s, String defaultText) {
        if (TextUtils.isEmpty(s)) {
            return defaultText;
        }
        return s;
    }

    /**
     * 处理double类型末尾的0
     */
    public static String subZeroAndDot(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return decimalFormat.format(number);
    }

    public static String removeChineseChar(String str) {
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }

    /**
     * 姓名保留姓名替换*
     */
    public static String replaceNameX(String str) {
        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
            i++;
            if (i == 1) {
                continue;
            }
            m.appendReplacement(sb, "*");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 判断字符串是否为空，如果为空则补充“”
     *
     * @param text 判断字符串
     * @return 处理后的数据
     */
    public static String suppleEmpty(String text) {
        return TextUtils.isEmpty(text) ? "" : text;
    }

    /**
     * 判断字符串是否为空，如果为空则补充symbol
     *
     * @param text   判断字符串
     * @param symbol 补充数据
     * @return 处理后的数据
     */
    public static String suppleEmpty(String text, String symbol) {
        return TextUtils.isEmpty(text) ? symbol : text;
    }

    /**
     * 判断字符串是否为空，如果为空则补充“--”
     *
     * @param text 判断字符串
     * @return 处理后的数据
     */
    public static String suppleEmptyWithLine(String text) {
        return TextUtils.isEmpty(text) ? "--" : text;
    }

    /**
     * 设置中文过滤
     */
    public static void setChineseTextView(EditText tv) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int i2, int i3) {
                for (int i = start; i < end; i++) {
                    char c = charSequence.charAt(i);
                    if (c >= 0x4E00 && c <= 0x9FA5) {
                        Log.i("setChineseTextView", "有中文 过滤");
                        return "";
                    }
                }
                return null;
            }
        };
        tv.setFilters(new InputFilter[]{filter});
        Log.i("setChineseTextView", "设置过滤");
    }

    /**
     * 复制文本
     *
     * @param context
     * @param text
     * @return
     */
    public static void copyText(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
        KYToastUtil.showNormal("已复制");
    }


    /**
     * 如果文本过长，获取带省略号的文本
     *
     * @param text      文本
     * @param maxLength 文本最大长度
     * @return 带省略号的文本
     */
    public static String getEllipsizedString(String text, int maxLength) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        if (text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        } else {
            return text;
        }
    }

}
