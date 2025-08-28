package com.kye.utils.keyboard;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.kye.utils.keyboard.callback.KeyboardShowCallback;


/**
 * @author lgj
 * @description:
 * @date : 2023/8/24 16:44
 */
public class KeyboardViewHelper {

    /**
     * 监听键盘显示隐藏
     * @param rootView
     * @param keyboardShowCallback
     */
    public static void checkKeyboardShowOrHide(View rootView, KeyboardShowCallback keyboardShowCallback){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = rootView.getHeight();
                int keypadHeight = screenHeight - rect.bottom;
                // 假设键盘高度占屏幕高度的15%以上，则认为键盘弹出
                if (keypadHeight > screenHeight * 0.15) {
                    // 键盘弹出时的操作
                    keyboardShowCallback.isKeyboardShow(true);
                } else {
                    // 键盘隐藏时的操作
                    keyboardShowCallback.isKeyboardShow(false);
                }
            }
        });
    }
}
