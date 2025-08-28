package com.kye.utils;

import android.graphics.Color;

/**
 * Routing
 * Desc 颜色工具类
 * Source
 * Created by Chase on 2019/2/14 15:22
 * Modify by Chase on 2019/2/14 15:22
 * Version 1.0
 */
public final class ColorUtil {

    private static final String TAG = "ColorUtil";

    private ColorUtil() {
    }

    /**
     * 获取半透明的颜色
     *
     * @param alphaPercent 透明度百分百 0f-1f
     * @param color
     * @return
     */
    public static int getAlphaColor(float alphaPercent, int color) {
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);
        int alpha = (int) (255 * alphaPercent);
        //Log.e("Chase","red="+red+" blue="+blue+" green="+green+" alpha="+alpha+" alphaPercent="+alphaPercent);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 根据fraction值来计算当前的颜色。 fraction值范围  0f-1f
     */
    public static int getCurrentColor(float fraction, int startColor, int endColor) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        int redCurrent = (int) (redStart + fraction * redDifference);
        int blueCurrent = (int) (blueStart + fraction * blueDifference);
        int greenCurrent = (int) (greenStart + fraction * greenDifference);
        int alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }

}
