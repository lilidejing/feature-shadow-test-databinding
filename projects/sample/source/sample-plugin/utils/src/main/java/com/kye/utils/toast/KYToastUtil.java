package com.kye.utils.toast;

import android.app.Application;
import android.view.Gravity;

import androidx.annotation.StringRes;

import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ViewToastStyle;
import com.kye.utils.pool.PlaySoundPool;
import com.kye.pda.biz.common.utils.R;
import com.kye.pda.utils.DensityUtils;

public class KYToastUtil {

    public static int VOICE_TYPE_SUCCESS = 0;

    public static int VOICE_TYPE_WAYBILL_SUCCESS = 1;
    private static Application mApplication;
    private static boolean mIsShowNormal = true;

    /**
     * 显示普通toast
     */
    public static void showNormal(CharSequence text) {
        if (!mIsShowNormal) {
            switchToNormal();
        }
        ToastUtils.show(text);
        mIsShowNormal = true;
    }

    /**
     * 显示普通toast
     */
    public static void showNormal(@StringRes int text) {
        if (!mIsShowNormal) {
            switchToNormal();
        }
        ToastUtils.show(mApplication.getString(text));
        mIsShowNormal = true;
    }

    /**
     * 切换到普通toast样式
     */
    private static void switchToNormal() {
        ToastUtils.setStyle(new ViewToastStyle(R.layout.view_toast_custom_normal, ToastUtils.getStyle()));
        ToastUtils.setGravity(Gravity.TOP, 0, DensityUtils.getDimenInt(mApplication, R.dimen.dp_110));
    }

    /**
     * 显示成功toast
     */
    public static void showSuccess(CharSequence text) {
        ToastUtils.setView(R.layout.view_toast_custom_success);
        ToastUtils.show(text);
        mIsShowNormal = false;
    }

    /**
     * 带播放语音的成功Toast
     *
     * @param text
     * @param voiceType 0，播放“成功”；1，播放“报单成功”
     */
    public static void showSuccessWithVoice(CharSequence text, int voiceType) {
        showSuccess(text);
        if (voiceType == VOICE_TYPE_WAYBILL_SUCCESS) {
            PlaySoundPool.getInstance(mApplication).playWaybillSuccessSound();
        } else {
            PlaySoundPool.getInstance(mApplication).playSuccessSound();
        }
    }

    /**
     * 失败toast的语音
     */
    public static void showFailWithVoice(CharSequence text, int voiceType) {
        showFail(text);
        PlaySoundPool.getInstance(mApplication).playSound(voiceType);
    }

    /**
     * 显示失败toast
     */
    public static void showFail(CharSequence text) {
        ToastUtils.setView(R.layout.view_toast_custom_fail);
        ToastUtils.show(text);
        mIsShowNormal = false;
    }

    /**
     * 重置为普通Toast样式
     */
    public static void init(Application application) {
        mApplication = application;
        initNormal();
    }

    /**
     * 重置为普通Toast样式
     */
    public static void initNormal() {
        ViewToastStyle toastStyle = new ViewToastStyle(R.layout.view_toast_custom_normal, ToastUtils.getStyle());
        ToastUtils.init(mApplication, null, toastStyle);
        ToastUtils.setGravity(Gravity.TOP, 0, DensityUtils.getDimenInt(mApplication, R.dimen.dp_110));
    }
}
