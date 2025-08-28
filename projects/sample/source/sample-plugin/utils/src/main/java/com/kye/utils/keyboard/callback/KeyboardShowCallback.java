package com.kye.utils.keyboard.callback;


/**
 * @author lgj
 * @description: 键盘显示回调
 * @date : 2023/8/24 12:33
 */
public interface KeyboardShowCallback {
    /**
     * 键盘是否显示
     * @param isShow true，显示；false,不显示
     */
   void isKeyboardShow(boolean isShow);
}
