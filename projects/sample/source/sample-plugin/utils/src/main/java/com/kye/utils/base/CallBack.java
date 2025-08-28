package com.kye.utils.base;

/**
 * @author 徐伟涛
 * @description: 基类接口回调
 * @date : 2022/12/8 15:57
 */
public interface CallBack<T> {

    /**
     * 成功
     *
     * @param result
     */
    void onSuccess(T result);

    /**
     * 失败
     *
     * @param msg
     */
    void onFail(String msg);

}
