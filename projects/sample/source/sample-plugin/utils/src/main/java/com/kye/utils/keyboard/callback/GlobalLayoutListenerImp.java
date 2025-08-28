package com.kye.utils.keyboard.callback;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.kye.utils.keyboard.core.KeyBoardEventBus;


/**
 * 用于真正的监听布局变化的回调类
 *
 * @author Simon
 */

public class GlobalLayoutListenerImp implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "GlobalLayoutListenerImp";
    private Activity activity;
    private IkeyBoardCallback ikeyBoardCallback;
    private final int NONE = -1;
    private final int SHOW = 1;
    private final int HIDDEN = 2;
    private int status = NONE;
    private View targetView;//被遮盖的view
    private int limitHeight;//被遮盖高度阈值


    public GlobalLayoutListenerImp(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onGlobalLayout() {
        //activity为null不执行
        if (activity == null) {
            return;
        }
        //获取可视范围
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕高度
        int screenHeight = KeyBoardEventBus.getDefault().getFullScreenHeight(activity);
        //获取状态栏高度
        int statusBarHeight = KeyBoardEventBus.getDefault().getStatusBarHeight(activity);

        //获取被遮挡高度（键盘高度）(屏幕高度-状态栏高度-可视范围)
        int keyBoardHeight = screenHeight - statusBarHeight - r.height();

        //显示或者隐藏
        boolean isCover= isCover(keyBoardHeight);

        //当首次或者和之前的状态不一致的时候会回调，反之不回调(用于当状态变化后才回调，防止多次调用)
        Log.d(TAG, "status="+status+" isCover="+isCover);
        if (status == NONE || (isCover && status == HIDDEN) || (!isCover && status == SHOW)) {
            if (isCover) {
                status = SHOW;
                dispatchKeyBoardShowEvent();
            } else {
                status = HIDDEN;
                dispatchKeyBoardHiddenEvent();
            }
        }
    }

    /**判断输入法已出现*/
    private boolean hasKeyBoard(int keyBoardHeight){
        if(keyBoardHeight>0){
            return true;
        }
        return false;
    }

    /**判断输入法遮盖区域*/
    private boolean isCover(int keyBoardHeight){
        int limitHeight=getLimitHeight();
        Log.d(TAG, "isCover: limitHeight="+limitHeight+" keyBoardHeight="+keyBoardHeight);
        if(keyBoardHeight>=limitHeight){
            return true;
        }
        return false;
    }

    /**设置遮盖阈值*/
    private int getLimitHeight(){
        //赋值一次就行
        if(limitHeight!=0){
            return limitHeight;
        }
        limitHeight=100;//默认值
        //获取屏幕高度
        int screenHeight = KeyBoardEventBus.getDefault().getFullScreenHeight(activity);
        //获取状态栏高度
        int statusBarHeight = KeyBoardEventBus.getDefault().getStatusBarHeight(activity);
        if(targetView !=null){
            int[] location = new int[2];
            // 获取targetView在窗体的坐标
            targetView.getLocationInWindow(location);
            limitHeight=screenHeight-statusBarHeight-location[1];
        }
        return limitHeight;
    }

    /**
     * 设置监听回调
     *
     * @param callback 监听的回调类
     */
    public void setCallback(Object callback) {
        if (!(callback instanceof IkeyBoardCallback)) {
            return;
        }
        ikeyBoardCallback=(IkeyBoardCallback) callback;
    }


    /**
     * 清除内部内存引用
     */
    public void release() {
        status = NONE;
        activity = null;
        ikeyBoardCallback = null;
    }

    /**
     * 隐藏事件
     */
    private void dispatchKeyBoardHiddenEvent() {
        if (ikeyBoardCallback == null) {
            return;
        }
        ikeyBoardCallback.onKeyBoardHidden();
    }

    /**
     * 显示事件
     */
    private void dispatchKeyBoardShowEvent() {
        if (ikeyBoardCallback == null) {
            return;
        }
        ikeyBoardCallback.onKeyBoardShow();
    }

    public void setTargetView(View toView){
        this.targetView =toView;
    }
}
