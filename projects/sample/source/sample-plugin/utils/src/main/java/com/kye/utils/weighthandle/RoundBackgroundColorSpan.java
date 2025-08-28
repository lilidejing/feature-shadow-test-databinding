package com.kye.utils.weighthandle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;

/**
 * Author:lgj
 * E-mail:614759@ky-tech.com.cn
 * Date:2022/8/29
 * Dec:
 * Version:1.0.0
 */
public class RoundBackgroundColorSpan extends ReplacementSpan {

    private int mRadius;
    private int bgColor;
    private int textColor;
    private int mSize;
    private int fillColor;
    private float strokeWidth;
    private float horizontalPadding;
    private float verticalPadding;
    private float horizontalFillPadding;
    private float verticalFillPadding;
    private float mTextSize;

    public RoundBackgroundColorSpan(int bgColor,
                                    int textColor,
                                    int radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.mRadius = radius;
    }

    public RoundBackgroundColorSpan(int bgColor,
                                    int fillColor,
                                    int textColor,
                                    int radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.mRadius = radius;
        this.fillColor = fillColor;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    public void setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    public void setHorizontalFillPadding(float horizontalFillPadding) {
        this.horizontalFillPadding = horizontalFillPadding;
    }

    public void setVerticalFillPadding(float verticalFillPadding) {
        this.verticalFillPadding = verticalFillPadding;
    }


    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    /**
     * @param start 第一个字符的下标
     * @param end   最后一个字符的下标
     * @return span的宽度
     */
    @Override
    public int getSize(@NonNull Paint paint,
                       CharSequence text,
                       int start,
                       int end,
                       Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius);
        return mSize + 5;//5:距离其他文字的空白
    }

    /**
     * @param y baseline
     */
    @Override
    public void draw(@NonNull Canvas canvas,
                     CharSequence text,
                     int start,
                     int end,
                     float x,
                     int top,
                     int y, int bottom,
                     @NonNull Paint paint) {
        int defaultColor = paint.getColor();//保存文字颜色
        float defaultStrokeWidth = paint.getStrokeWidth();


        paint.setColor(fillColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);
        paint.setTextSize(mTextSize);
        paint.setAntiAlias(true);
//        paint.setColorFilter()
        RectF rectF2 = new RectF(x - horizontalFillPadding + 2.5f, y + paint.ascent() - verticalFillPadding + 2.5f, x + mSize + horizontalFillPadding - 2.5f, y + paint.descent() + verticalFillPadding - 2.5f);
        canvas.drawRoundRect(rectF2, mRadius, mRadius, paint);

        //绘制圆角矩形
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.strokeWidth);
        paint.setAntiAlias(true);
//        paint.setColorFilter()
        RectF rectF = new RectF(x - horizontalPadding + 2.5f, y + paint.ascent() - verticalPadding + 2.5f, x + mSize + horizontalPadding - 2.5f, y + paint.descent() + verticalPadding - 2.5f);
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。
        // paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        //x+2.5f解决线条粗细不一致问题
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);

        //绘制文字
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(defaultStrokeWidth);
        canvas.drawText(text, start, end, x + mRadius, y, paint);//此处mRadius为文字右移距离

        paint.setColor(defaultColor);//恢复画笔的文字颜色
    }
}
