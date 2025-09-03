package com.tencent.shadow.sample.plugin.app.lib.gallery.basetoolbar


import android.content.Context
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kye.foundation.component.basetoolbar.IBaseToolbarView
import com.tencent.shadow.sample.plugin.app.lib.base.R
import java.lang.reflect.Field
import java.util.logging.Logger

/**
 * @author MEIJING
 * @description: toolbar管理类，仅给BaseToolbarDelegate调用
 * @date : 2024/10/16 9:25
 */
class BaseToolbarManager(
    activity: AppCompatActivity?,
    mToolbar: Toolbar?
) : IBaseToolbarView {

    /**
     * toolbar
     */
    private var mToolbar: Toolbar? = null
    private var mActionBar: ActionBar? = null

    /**
     * 菜单
     */
    private var menuId: Int = -1

    init {
        initToolbar(activity, mToolbar)
    }

    /**
     * 初始化toolbar
     */
    private fun initToolbar(
        activity: AppCompatActivity?,
        mToolbar: Toolbar?
    ) {

        this.mToolbar = mToolbar
        if (null != mToolbar) {
            activity?.setSupportActionBar(mToolbar)
        }
        this.mActionBar = activity?.supportActionBar


    }


    /**
     * 设置导航图标 返回按钮
     */
    override fun setToolbarBackground(backgroundResId: Int) {
        mToolbar?.setBackgroundResource(backgroundResId)
    }

    /**
     * 设置导航图标 返回按钮
     */
    override fun setToolbarNavigationIcon(navigationResId: Int) {
        mToolbar?.setNavigationIcon(navigationResId)

    }

    override fun setBackListener(action: () -> Unit) {
        mToolbar?.setNavigationOnClickListener {
            action()
        }
        initToolbarChildViewId()
    }

    private fun initToolbarChildViewId() {
        try {
            if (null != mToolbar) {
                val mToolbarClass = mToolbar!!::class.java
                val mNavButtonViewField: Field? = mToolbarClass.getDeclaredField("mNavButtonView")
                mNavButtonViewField?.isAccessible = true
                val navButtonView = mNavButtonViewField?.get(mToolbar) as? View
                navButtonView?.id = R.id.iv_back
            }
        } catch (e: Exception) {

        }
    }

    override fun removeAllChild() {
        mToolbar?.removeAllViews()
    }

    /**
     * 设置logo
     */
    override fun setToolbarLogo(logoResId: Int) {
        mToolbar?.setLogo(logoResId)
    }

    /**
     * 设置Title
     */
    override fun setToolbarTitle(title: String?) {
        mActionBar?.title = if (TextUtils.isEmpty(title)) "" else title
    }

    override fun setToolbarTitleTextColor(titleResId: Int) {
        mToolbar?.setTitleTextColor(titleResId)
    }

    override fun setTitleTextAppearance(context: Context?, styleResId: Int) {
        // 设置 Toolbar 标题的文本样式
        mToolbar?.setTitleTextAppearance(context, styleResId)
    }

    /**
     * 设置Title
     */
    override fun setToolbarSubtitle(subtitle: String?) {
        mActionBar?.subtitle = if (TextUtils.isEmpty(subtitle)) "" else subtitle
    }

    /**
     * 设置Title
     */
    override fun setToolbarSubtitleTextColor(colorId: Int) {
        mToolbar?.setSubtitleTextColor(colorId)
    }

    override fun setSubtitleTextAppearance(context: Context?, styleResId: Int) {
        // 设置 Toolbar 标题的文本样式
        mToolbar?.setSubtitleTextAppearance(context, styleResId)
    }

    /**
     * 添加菜单
     */
    override fun setToolbarMenu(menuId: Int) {
        try {
            this.menuId = menuId
            mToolbar?.inflateMenu(menuId)
        } catch (e: Exception) {

        }
    }

    override fun setOnMenuItemClickListener(action: (item: MenuItem) -> Unit) {
        mToolbar?.setOnMenuItemClickListener { item ->
            item?.let {
                action(it)
            }
            true
        }
    }

    /**
     * toolbar 显示和隐藏
     */
    override fun showToolbar(isShow: Boolean) {
        if (isShow) {
            mToolbar?.visibility = View.VISIBLE
        } else {
            mToolbar?.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menuInflater: MenuInflater?, menu: Menu?): Boolean {
        if (menuId != -1) {
            menuInflater?.inflate(menuId, menu)
        }
        return true
    }

    override fun getToolBar(): Toolbar? {
        return mToolbar
    }


}