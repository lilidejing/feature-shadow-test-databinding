package com.tencent.shadow.sample.plugin.app.lib.gallery.second.basetoolbar


import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * @author MEIJING
 * @description: toolbar代理类，外部调用类
 * @date : 2024/10/16 9:25
 */
class BaseToolbarDelegate(
    activity: AppCompatActivity?,
    mToolbar: Toolbar?
) : IBaseToolbarView {


    /**
     * toolbar管理类
     */
    private var mBaseToolbarManager: BaseToolbarManager? = null

    init {
        mBaseToolbarManager = BaseToolbarManager(activity, mToolbar)
    }

    /**
     * 设置导航图标 返回按钮
     */
    override fun setToolbarBackground(backgroundResId: Int) {
        mBaseToolbarManager?.setToolbarBackground(backgroundResId)
    }

    /**
     * 设置导航图标 返回按钮
     */
    override fun setToolbarNavigationIcon(navigationResId: Int) {
        mBaseToolbarManager?.setToolbarNavigationIcon(navigationResId)

    }

    override fun setBackListener(action: () -> Unit) {
        mBaseToolbarManager?.setBackListener(action)
    }

    /**
     * 设置logo
     */
    override fun setToolbarLogo(logoResId: Int) {
        mBaseToolbarManager?.setToolbarLogo(logoResId)
    }

    /**
     * 设置Title
     */
    override fun setToolbarTitle(title: String?) {
        mBaseToolbarManager?.setToolbarTitle(title)
    }

    /**
     * 设置标题的文字颜色
     */
    override fun setToolbarTitleTextColor(titleResId: Int) {
        mBaseToolbarManager?.setToolbarTitleTextColor(titleResId)

    }

    /**
     * 设置标题的文字样式
     */
    override fun setTitleTextAppearance(context: Context?, styleResId: Int) {
        mBaseToolbarManager?.setTitleTextAppearance(context, styleResId)
    }

    /**
     * 设置Title
     */
    override fun setToolbarSubtitle(subtitle: String?) {
        mBaseToolbarManager?.setToolbarSubtitle(subtitle)
    }

    /**
     * 设置Title
     */
    override fun setToolbarSubtitleTextColor(colorId: Int) {
        mBaseToolbarManager?.setToolbarSubtitleTextColor(colorId)
    }

    override fun setSubtitleTextAppearance(context: Context?, styleResId: Int) {
        // 设置 Toolbar 标题的文本样式
        mBaseToolbarManager?.setSubtitleTextAppearance(context, styleResId)
    }

    /**
     * 添加菜单
     */
    override fun setToolbarMenu(menuId: Int) {
        mBaseToolbarManager?.setToolbarMenu(menuId)
    }

    override fun setOnMenuItemClickListener(action: (item: MenuItem) -> Unit) {
        mBaseToolbarManager?.setOnMenuItemClickListener(action)
    }

    /**
     * toolbar 显示和隐藏
     */
    override fun showToolbar(isShow: Boolean) {
        mBaseToolbarManager?.showToolbar(isShow)
    }


    override fun onCreateOptionsMenu(menuInflater: MenuInflater?, menu: Menu?): Boolean {
        mBaseToolbarManager?.let {
            return it.onCreateOptionsMenu(menuInflater, menu)
        }
        return false
    }

    override fun getToolBar(): Toolbar? {
        return mBaseToolbarManager?.getToolBar()
    }

    /**
     * 移除子布局
     */
    override fun removeAllChild() {
        mBaseToolbarManager?.removeAllChild()
    }

}