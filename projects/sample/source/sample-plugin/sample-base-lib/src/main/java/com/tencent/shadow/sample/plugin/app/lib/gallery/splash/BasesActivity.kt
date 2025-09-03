package com.tencent.shadow.sample.plugin.app.lib.gallery.splash


import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.kye.foundation.component.ext.saveAsUnChecked

import com.tencent.shadow.sample.plugin.app.lib.base.R
import com.tencent.shadow.sample.plugin.app.lib.gallery.basetoolbar.BaseToolbarDelegate


/**
 * @author MEIJING
 * @description: Activity 基类，所有上层的activity都必须以此为基类
 * 主要职责：
 *  1.负责统一添加将业务布局添加到根布局中
 *  2.提供基础方法调用顺序
 *  3.toolbar的管理
 *  4.加载状态视图管理
 *  5.加载中的dialog弹框处理
 *  6.事件分发对键盘的处理
 * @date : 2024/10/14 9:25
 */
abstract class BasesActivity : AppCompatActivity() {

    companion object {
        /**
         * 进度条最长时间
         */
        const val PROGRESS_DEFAULT_SHOW_TIME = 6000
    }

    /**
     * 加载中 菊花dialog
     */
    private var mDialog: Dialog? = null

    /**
     * 是否开启点击editView以外的地方就隐藏键盘，默认为 true
     */
    private var enableEditViewHideKeyboard = true



    /**
     * toolbar
     */
    private var mBaseToolBar: Toolbar? = null


    /**
     * toolbar代理类
     */
    private var mBaseToolbarDelegate: BaseToolbarDelegate? = null

    private var mBusinessBinding: ViewDataBinding? = null  // 新增：存储业务绑定的实例

    override fun onCreate(savedInstanceState: Bundle?) {
        //在onCreate()之前调用  该方法非必须，请在需要时重写
        preCreate()
        setTheme(R.style.PluginAppTheme)
        super.onCreate(savedInstanceState)
        //设置布局
        val rootView = customContentView(View.inflate(this, R.layout.activity_base_layout, null))
        setContentView(rootView)
        //初始化沉浸式状态栏
//        initImmersionBar()
        //初始化标题栏
        initToolBar()
        //基础层需要做一些数据初始化View操作 给基础层调用 业务端不要调用
        initPreData()
        //初始化数据
        initData(savedInstanceState)
        //用于对View进行一些初始化操作
        initView()
        //用于初始化各种View事件
        initEvent()
        //基础层需要做一些基础操作  给基础层调用  业务端不要调用
        initBaseOperation()
    }

    /**
     * 设置内容布局
     */
    private fun customContentView(rootView: View): View {
        val flContent = rootView.findViewById<FrameLayout>(R.id.flContainer)
        val layoutId = getLayoutResId()
        if (layoutId <= 0) {
            return rootView
        }
        flContent?.apply {
            val content = View.inflate(this@BasesActivity, layoutId, null)
            content?.let {
                addView(it, 0)
            }
        }
        return rootView
    }


    //若使用ViewDataBinding，想要获取业务端对应的ViewDataBinding，请调用该方法生成
    fun <T : ViewDataBinding> createViewDataBinding(): T? {
        val flContent = findViewById<FrameLayout>(R.id.flContainer)
        val flContentChildView = flContent.getChildAt(0)
        flContentChildView?.let {
            return DataBindingUtil.bind(it)
        }
        return null
    }


    /**
     * 设置内容布局（修改：使用DataBindingUtil.inflate以支持绑定，避免bind失败）
     */
    /*private fun customContentView(rootView: View): View {
        val flContent = rootView.findViewById<FrameLayout>(R.id.flContainer)
        val layoutId = getLayoutResId()
        if (layoutId <= 0) {
            return rootView
        }
        flContent?.apply {
            try {
                // 尝试用DataBindingUtil.inflate（假设业务布局是绑定布局）
                val binding: ViewDataBinding =
                    DataBindingUtil.inflate(layoutInflater, layoutId, this, false)
                addView(binding.root)  // 添加根视图到容器
                mBusinessBinding = binding  // 存储绑定实例
                binding.lifecycleOwner = this@BaseActivity  // 可选：设置lifecycle以支持LiveData
            } catch (e: Exception) {
                // 如果不是绑定布局（e.g., 无<layout>根），fallback到普通
                Log.e("lgj","DataBinding inflate failed, fallback to normal inflate: ${e.message}")
                val content = View.inflate(this@BaseActivity, layoutId, null)
                content?.let {
                    addView(it, 0)
                }
            }
        }
        return rootView
    }

    *//**
     * 若使用ViewDataBinding，想要获取业务端对应的ViewDataBinding，请调用该方法生成
     * （修改：直接返回存储的绑定实例，无需后续bind孩子视图，避免"not a binding layout"）
     *//*
    fun <T : ViewDataBinding> createViewDataBinding(): T? {
        mBusinessBinding?.let {
            return it as? T  // 安全cast到泛型T（子类指定，如ActivityHomeBinding）
        }
        // 如果未存储（e.g., 非绑定布局），返回null或log警告
        Log.w("lgj","No DataBinding instance available. Ensure business layout is a binding layout.")
        return null
    }*/


    /**
     * 若使用ViewBinding，想要获取业务端对应的ViewBinding，请调用该方法生成
     */
    fun <T : ViewBinding> createViewBinding(vbClass: Class<T>): T? {
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        val mBinding = method.invoke(this, layoutInflater)
        mBinding?.let {
            return mBinding.saveAsUnChecked()
        }
        return null
    }


    /**
     *  在onCreate()之前调用
     *  该方法非必须，请在需要时重写
     */
    protected open fun preCreate() {

    }

    /**
     * 初始化banging定义的方法 用于兼容当前项目
     */
    protected  open fun initDataBinding(){

    }

    /**
     * 初始化视图
     * @return Int 布局id
     */
    abstract fun getLayoutResId(): Int

    /**
     * 用于对View进行一些初始化操作
     */
    protected abstract fun initView()


    /**
     * 用于初始化各种事件
     */
    protected abstract fun initEvent()

    /**
     * 初始化数据
     * @param savedInstanceState Bundle?
     */
    protected abstract fun initData(savedInstanceState: Bundle?)


    /**
     * 基础层需要做一些数据初始化View操作  不要向业务层暴露，业务层不可重写
     */
    protected open fun initPreData() {
        initDataBinding()
    }

    /**
     * 基础层需要做一些基础操作  给基础层调用  业务端不要调用
     */
    protected open fun initBaseOperation() {

    }

    /**
     * 初始化沉浸式状态栏
     */
    private fun initImmersionBar() {
        if (resetStatusBarColor()) {
            setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light)
        } else {
            setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light)
        }
    }

    // 新建状态栏颜色
    protected open fun newStatusBarColor(): Int {
        return R.color.common_color_functional_gray_no1
    }

    // 是否设置状态栏颜色
    protected open fun resetStatusBarColor(): Boolean {
        return false
    }

    // 是否设置成透明状态栏，即就是全屏模式
    open fun isUseFullScreenMode(): Boolean {
        return false
    }

    /**
     * 初始化toolbar
     */
    private fun initToolBar() {
        mBaseToolBar = findViewById(R.id.baseToolbar)
        mBaseToolbarDelegate = BaseToolbarDelegate(this, mBaseToolBar)
        mBaseToolbarDelegate?.setBackListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mBaseToolbarDelegate?.let {
            return it.onCreateOptionsMenu(menuInflater, menu)
        }
        return false
    }

    /**
     * 获取toolbar处理的代理类
     */
    fun getBaseToolbarDelegate(): BaseToolbarDelegate? {
        return mBaseToolbarDelegate
    }

    /**
     * 自定义Toolbar
     */
    fun setCustomToolbar(view: View?) {
        view?.let {
            mBaseToolbarDelegate?.removeAllChild()
            //第一次设置自定义View
            it.layoutParams = ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            mBaseToolBar?.addView(it, 0)
        }
    }


    /**
     * 显示加载中……弹框  默认
     */
    open fun showLoading() {
        showLoading(false, PROGRESS_DEFAULT_SHOW_TIME)
    }

    /**
     * 显示加载中……弹框  设置是否可以点击取消
     */
    open fun showLoading(cancelable: Boolean) {
        showLoading(cancelable, PROGRESS_DEFAULT_SHOW_TIME)
    }

    /**
     * 显示加载中……弹框  设置显示的时长
     */
    open fun showLoading(duration: Int) {
        showLoading(false, duration)
    }

    /**
     * 显示加载中……弹框  设置显示的时长/是否可以点击取消
     */
    open fun showLoading(cancelable: Boolean, duration: Int) {
        showProgressDialog(duration, cancelable)
    }

    /**
     * 隐藏加载中……弹框
     */
    open fun hideLoading() {
        mDialog?.let {
            it.setOnDismissListener(null)
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    /**
     * 是否正在加载中
     */
    open fun isLoading(): Boolean {
        mDialog?.let {
            return it.isShowing
        }
        return false
    }

    /**
     * 显示加载中……弹框
     */
    private fun showProgressDialog(time: Int, cancelable: Boolean) {

    }


    /**
     * 界面是否已经销毁
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected fun isActivityDestroy(): Boolean {
        // 页面正在销毁或者已经销毁，不显示Dialog。避免android.view.WindowManager$BadTokenException Unable to add window
        if (isFinishing || isDestroyed) {
            return true
        }
        return false
    }

    /**
     * 触摸到非EditText区域，隐藏键盘
     *
     * @param event
     * @return
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        try {
            return super.dispatchTouchEvent(event)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            return false
        }
    }

    protected fun enableEditViewHideKeyboard(isEnable: Boolean) {
        this.enableEditViewHideKeyboard = isEnable
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //页面异常退出的时候，关闭系统软键盘，避免出现内存泄漏


    }

    override fun onDestroy() {
        super.onDestroy()


    }

    open fun getBaseRootView(): View? {
        return (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }


    /**
     * 界面是否需要显示网络异常 默认需要显示
     * 但是某些页面只是用来辅助 比如拍照页面/地址选择/activity的dialog这类的 不需要的 则设置为false
     */
    open fun isShowNetWorkTip(): Boolean {
        return true
    }


    /**
     * 设置页面背景
     * @param drawableRes 资源
     */
    open fun setBaseBackground(drawableRes: Int) {
        findViewById<View>(R.id.containerView).setBackgroundResource(drawableRes)
    }

}
