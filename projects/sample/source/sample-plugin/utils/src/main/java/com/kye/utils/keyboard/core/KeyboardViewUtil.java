package com.kye.utils.keyboard.core;

import android.graphics.Rect;
import android.view.View;

import com.kye.pda.utils.DensityUtils;

public class KeyboardViewUtil {
    private static int scrollHeight;

    /**获取滚动高度*/
    private static int getScrollHeight(View root,View targetView){
        Rect rect = new Rect();
        // 获取root在窗体的可视区域
        root.getWindowVisibleDisplayFrame(rect);
        int[] location = new int[2];
        // 获取targetView在窗体的坐标
        targetView.getLocationInWindow(location);
        // 计算root滚动高度，使targetView在可见区域
        int scrollHeight = (location[1] + targetView.getHeight() + DensityUtils.dip2px(root.getContext(),10)) - rect.bottom;
        return scrollHeight;
    }
    /**获取view在屏幕中的高度位置*/
    public static int getHeight(View targetView){
        // 获取root在窗体的可视区域
        int[] location = new int[2];
        // 获取targetView在窗体的坐标
        targetView.getLocationInWindow(location);
        int height = location[1];
        return height;
    }
    /**页面向上滑动*/
    public static void scrollUp(View scrollView, View targetView) {
        if(scrollHeight==0){
            scrollHeight=getScrollHeight(scrollView,targetView);
        }
        scrollView.scrollTo(0, scrollHeight);
    }
    /**页面向上滑动固定高度*/
    public static void scrollUpByFixedHeight(View scrollView,int height) {
        if(scrollHeight==0){
            scrollHeight=height;
        }
        scrollView.scrollTo(0, scrollHeight);
    }

    /**页面向下滑动*/
    public static void scrollDown(View scrollView) {
        scrollView.scrollTo(0, 0);
    }
}