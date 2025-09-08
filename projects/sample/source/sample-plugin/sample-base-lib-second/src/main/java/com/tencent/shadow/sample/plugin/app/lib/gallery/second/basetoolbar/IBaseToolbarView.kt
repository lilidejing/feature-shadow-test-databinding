package com.tencent.shadow.sample.plugin.app.lib.gallery.second.basetoolbar

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

/**
 * @author MEIJING
 * @description: toolbar向外暴露的接口
 * @date : 2024/10/16 9:25
 */
interface IBaseToolbarView {


    /**
     * 设置导航图标 返回按钮
     */
    fun setToolbarBackground(backgroundResId: Int)

    /**
     * 设置导航图标 返回按钮
     */
    fun setToolbarNavigationIcon(navigationResId: Int)

    /**
     * 设置返回监听
     */
    fun setBackListener(action: () -> Unit)

    /**
     * 设置logo
     */
    fun setToolbarLogo(logoResId: Int)

    /**
     * 设置Title
     */
    fun setToolbarTitle(title: String?)

    /**
     * 设置Title的字体颜色
     */
    fun setToolbarTitleTextColor(titleResId: Int)

    /**
     * 设置标题文本样式
     */
    fun setTitleTextAppearance(context: Context?, styleResId: Int)

    /**
     * 设置Title
     */
    fun setToolbarSubtitle(subtitle: String?)

    /**
     * 设置副标题文本颜色
     */
    fun setToolbarSubtitleTextColor(colorId: Int)

    /**
     * 设置副标题文本样式
     */
    fun setSubtitleTextAppearance(context: Context?, styleResId: Int)

    /**
     * 添加菜单
     */
    fun setToolbarMenu(menuId: Int)

    /**
     * 设置菜单监听
     */
    fun setOnMenuItemClickListener(action: (item: MenuItem) -> Unit)

    /**
     * toolbar 显示和隐藏
     */
    fun showToolbar(isShow: Boolean)
    fun onCreateOptionsMenu(menuInflater: MenuInflater?, menu: Menu?): Boolean

    /**
     * 获取toolbar高度
     */
    fun getToolBar(): Toolbar?
    fun removeAllChild()
}