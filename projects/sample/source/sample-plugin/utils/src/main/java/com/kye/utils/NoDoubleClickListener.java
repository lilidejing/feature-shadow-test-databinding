package com.kye.utils;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 * 避免在1秒内出发多次点击
 */
public abstract class NoDoubleClickListener implements OnClickListener {
    private int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;
    private int id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int mId = v.getId();
        if (id != mId) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            onNoDoubleClick(v);
        }
        lastClickTime = currentTime;
    }

    protected abstract void onNoDoubleClick(View v);

    /**
     * 自定义防抖动时间
     * @param delayTime 防抖动时间
     */
    public void setClickDelayTime(int delayTime) {
        this.MIN_CLICK_DELAY_TIME = delayTime;
    }
}
