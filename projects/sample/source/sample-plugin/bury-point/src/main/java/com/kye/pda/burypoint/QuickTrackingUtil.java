package com.kye.pda.burypoint;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.efs.sdk.h5pagesdk.H5Manager;
import com.efs.sdk.launch.LaunchManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.process.DBPathAdapter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.spm.SpmAgent;
import com.umeng.umcrash.UMCrash;
import com.umeng.umefs.UMEfs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : gsy
 * @description : 埋点工具类
 * @date : 2024-11-07
 */
public class QuickTrackingUtil {
    /**
     * 对应app key
     */
    private static final String APP_KEY_UAT = "";
    /**
     * 对应生产app key
     */
    private static final String APP_KEY_PRO = "";
    /**
     * 对应渠道
     */
    private static final String APP_CHANNEL = "";
    /**
     * uat QT收数域名
     */
    private static final String UAT_DOMAIN_CUSTOM = "";
    /**
     * uat APM收数域名
     */
    private static final String UAT_DOMAIN_APM = "";
    /**
     * QT收数域名
     */
    private static final String PRO_DOMAIN_CUSTOM = "";
    /**
     * APM收数域名
     */
    private static final String PRO_DOMAIN_APM = "";
    private final Map<String, Object> mEventMap = new HashMap<>();
    /**
     * 上下文，和用来判断是否初始化
     */
    private Application mApplication;
    private static QuickTrackingUtil INSTANCE;
    private boolean mHasPreInit = false;
    /**
     * H5 APM(新平台) DEV
     */
    private static final String UMAPPKEY_H5_APM_DEV = "";
    /**
     * H5 APM(新平台) PRO
     */
    private static final String UMAPPKEY_H5_APM_PRO = "";
    /**
     * APM收数域名 DEV
     */
    private static final String UM_DOMAIN_APM_DEV = "";
    /**
     * APM收数域名 PRO
     */
    private static final String UM_DOMAIN_APM_PRO = "";

    /**
     * 配置是否可以上报埋点
     */
    private boolean isCanReport = true;
    /**
     * 是否是主进程
     */
    private boolean mIsMainProcess = true;

    public boolean isCanReport() {
        return isCanReport;
    }

    public void setCanReport(boolean canReport) {
        isCanReport = canReport;
    }

    private String mUmAppKeyH5Apm;
    private String mUmDomainApm;

    public static final String UM_APM_H5_JS_CODE = "void (function (e, t, n, a, o, i, m) {" +
            "                                (e._um_apm_namespace = o)," +
            "                                (e[o] =" +
            "                                    e[o] ||" +
            "                                    function () {" +
            "                                    (e[o].q = e[o].q || []).push(arguments);" +
            "                                    })," +
            "                                (e[o].l = e[o].l || +new Date())," +
            "                                (i = t.createElement(n))," +
            "                                i.setAttribute('crossorigin', '')," +
            "                                (i.src = a)," +
            "                                (m = t.getElementsByTagName(n)[0])," +
            "                                m.parentNode.insertBefore(i, m);" +
            "                            })(window, document, 'script', 'https://bg-prd-cos-bdp-1257092428.cos.ap-guangzhou.myqcloud.com/bigdata/kyelog/apm_v1_md5_6654.js', '_apm');" +
            "                            _apm('create',{" +
            "                                pageFilter: { mode: 'ignore', rules: [] }," +
            "                                pkgList: ['com.kye.pad','com.seuic.kysy']," +
            "                            });";


    public static QuickTrackingUtil getInstance() {
        if (null == INSTANCE) {
            synchronized (QuickTrackingUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new QuickTrackingUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * @param moduleName     模块名
     * @param employeeNumber 工号
     * @return 结果
     */
    public String generateUmApmH5JsCode(String moduleName, String employeeNumber) {
        return "if (!document.getElementById('apm_h5_sdk')) {" +
                "   void (function (e, t, n, a, o, i, m) {" +
                "                                (e._um_apm_namespace = o)," +
                "                                (e[o] =" +
                "                                    e[o] ||" +
                "                                    function () {" +
                "                                    (e[o].q = e[o].q || []).push(arguments);" +
                "                                    })," +
                "                                (e[o].l = e[o].l || +new Date())," +
                "                                (i = t.createElement(n))," +
                "                                i.setAttribute('crossorigin', '')," +
                "                                (i.src = a)," +
                "                                (m = t.getElementsByTagName(n)[0])," +
                "                                m.parentNode.insertBefore(i, m);" +
                "                            })(window, document, 'script', 'https://g.alicdn.com/jssdk/apm/2.0.10/es5/uapm.iife.js', '_apm');" +
                "                            _apm('create',{" +
                "                                pid:'" +
                mUmAppKeyH5Apm + "'," +
                "                                dsn:'https://" +
                mUmDomainApm + "'," +
//                                    "                                logLevel: 3," +
                "                                pageFilter: { mode: 'ignore', rules: [] }," +
                "                                tag: '" +
                moduleName + "'," +
                "                            });" +
                "                            _apm('set','puid','" +
                employeeNumber + "" + "');" +
                "                            if (window && window.Vue) {" +
                "                               window.Vue.config.errorHandler = (err, vm, info) => {" +
                "                                   window._apm && window._apm('captureException', err)" +
                "                               }" +
                "                            };" +
                "}";
    }

    /**
     * 主进程预初始化, 只初始化QT埋点， BaseApplication 中执行
     */
    public void preMainInit(Application application, boolean isDebug, boolean initAllAuto) throws UMInitException {
        if (application == null) {
            throw new UMInitException("请初始化Application");
        }

        this.mApplication = application;

        mHasPreInit = true;
    }

    /**
     * 主进程预初始化，初始化QT和APM， BaseApplication 中执行
     *
     * @param application application的context
     */
    public void preMainInit(Application application, boolean isDebug) throws UMInitException {
        preMainInit(application, isDebug, false);
    }

    /**
     * 是否开启全埋点
     *
     * @param auto true=自动
     */
    public void initAuto(boolean auto) {
        // 开启全部 Fragment 页面浏览事件的自动采集功能
        MobclickAgent.enableFragmentPageCollection(auto);
        // 开启控件点击事件的自动采集功能。
        MobclickAgent.setAutoEventEnabled(auto);
    }

    /**
     * 主进程初始化， 用户同意安全协议后执行
     */
    public void init(boolean isDebug) throws UMInitException {
        if (mApplication == null) {
            throw new UMInitException("请初始化Application");
        }

        if (!mHasPreInit) {
            throw new UMInitException("请预初始化UM SDK");
        }
        // 是否主进程
        mIsMainProcess = true;


        if (isDebug) {
            UMConfigure.init(mApplication, APP_KEY_UAT, APP_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, "");
            UMEfs.init(mApplication, APP_KEY_UAT, APP_CHANNEL);
            UMCrash.init(mApplication, APP_KEY_UAT, APP_CHANNEL);
            mUmAppKeyH5Apm = UMAPPKEY_H5_APM_DEV;
            mUmDomainApm = UM_DOMAIN_APM_DEV;
        } else {
            UMConfigure.init(mApplication, APP_KEY_PRO, APP_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, "");
            UMEfs.init(mApplication, APP_KEY_PRO, APP_CHANNEL);
            UMCrash.init(mApplication, APP_KEY_PRO, APP_CHANNEL);
            mUmAppKeyH5Apm = UMAPPKEY_H5_APM_PRO;
            mUmDomainApm = UM_DOMAIN_APM_PRO;
        }
    }

    public static class UMInitException extends Exception {

        public UMInitException() {
            super();
        }

        public UMInitException(String message) {
            super(message);
        }
    }

    /**
     * 注册全局变量
     *
     * @param var1 所有全局变量的键值对关系， value只能是 String、Long、Integer、Float、Double、Short
     */
    public void registerGlobalProperties(Map<String, Object> var1) {
        if (mApplication == null) {
            return;
        }
        MobclickAgent.registerGlobalProperties(mApplication, var1);
    }

    /**
     * 删除指定全局变量，后续的操作都不会传这个变量
     *
     * @param key key
     */
    public void unregisterGlobalPropertyForKey(String key) {
        if (mApplication == null) {
            return;
        }
        MobclickAgent.unregisterGlobalProperty(mApplication, key);
    }

    /**
     * 清空全局变量
     */
    public void clearGlobalProperties() {
        if (mApplication == null) {
            return;
        }
        MobclickAgent.clearGlobalProperties(mApplication);
    }

    /**
     * 页面采集切换自动或者手工
     *
     * @param isAuto 是否自动
     */
    public void checkAutoOrManual(boolean isAuto) {
        if (mApplication == null) {
            return;
        }
        if (isAuto) {
            //自动采集模式
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        } else {
            //手动采集模式， 需要搭配onPageStart 和 onPageEnd 方法，分别在页面的onResume 和 onPause 调用
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
        }
    }

    /**
     * 页面起始
     *
     * @param pageKey 页面名称
     */
    public void onPageStart(String pageKey) {
        if (mApplication == null) {
            return;
        }
        MobclickAgent.onPageStart(pageKey);
    }

    /**
     * 页面结束
     *
     * @param pageKey 页面名称
     */
    public void onPageEnd(String pageKey) {
        if (mApplication == null) {
            return;
        }
        MobclickAgent.onPageEnd(pageKey);
    }

    /**
     * 页面浏览参数
     *
     * @param pageKey 页面名称
     */
    public void setPageProperty(String pageKey, Map<String, Object> user) {
        if (mApplication == null) {
            return;
        }
        SpmAgent.setPageProperty(mApplication, pageKey, user);
    }

    /**
     * 如果某个界面需要停止采集，则在onCreate里面调用这个方法，如果需要手动采集，则需要搭配onPageStart 和 onPageEnd 方法，分别在页面的onResume 和 onPause 调用
     *
     * @param context 当前页面context
     * @param tag     手动埋点时的自定义页面名称，该参数控制当前页面的手动埋点页面数据是否上报，传空则表示上报，传自定义页面名称则表示不上报。
     */
    public void skipMe(Context context, String tag) {
        if (mApplication == null || context == null) {
            return;
        }
        MobclickAgent.skipMe(context, tag);
    }

    /**
     * 账号统计，登陆成功后调用
     *
     * @param ID 用户id
     */
    public void onProfileSignIn(String ID) {
        if (mApplication == null) {
            return;
        }
        MobclickAgent.onProfileSignIn(ID);
    }

    /**
     * 设置用户属性
     *
     * @param user 用户集合
     */
    public void putUserProfile(Map<String, Object> user) {
        if (mApplication == null) {
            return;
        }
        onEventObject("$$_user_profile", user);
    }

    /**
     * 登出
     */
    public void onProfileSignOff() {
        if (mApplication == null) {
            return;
        }
        //登出
        MobclickAgent.onProfileSignOff();
    }

    /**
     * 只有宿主才能调用这个方法，插件进程需要调用onEventObject方法
     *
     * @param key      键
     * @param eventMap 事件集合
     */
    public void onMainEventObject(String key, Map<String, Object> eventMap) {
        if (mApplication == null) {
            return;
        }
        if (isCanReport()) {
            MobclickAgent.onEventObject(mApplication, key, eventMap);
        }
    }

    /**
     * 自定义事件，参考OEM自定义事件设计 https://docs.qq.com/sheet/DYU1Ba3h6cHd6YXdU?tab=BB08J2
     *
     * @param key      键
     * @param eventMap 事件集合
     */
    public void onEventObject(String key, Map<String, Object> eventMap) {
        if (mApplication == null) {
            return;
        }
        Log.i("buryEvent", "1 mIsMainProcess:" + mIsMainProcess + " isCanReport:" + isCanReport + " key:" + key);
        if (isCanReport()) {

            MobclickAgent.onEventObject(mApplication, key, eventMap);

        }
    }

    /**
     * 记录event的map
     *
     * @return 事件集合
     */
    public Map<String, Object> getEventMap() {
        return mEventMap;
    }

    public QuickTrackingUtil putEventMap(String key, Object value) {

        if (value != null && !TextUtils.isEmpty(value.toString())) {
            mEventMap.put(key, value);
        }
        return this;
    }

    /**
     * 自定义事件，参考OEM自定义事件设计 https://docs.qq.com/sheet/DYU1Ba3h6cHd6YXdU?tab=BB08J2
     * 自用类里面的eventMap，与putEventMap配合使用QuickTrackingUtil.putEventMap（）.onEventObject（）
     *
     * @param key 键
     */
    public void onMainEventObject(String key) {
        if (mApplication == null) {
            return;
        }
        Log.i("buryEvent", "2 mIsMainProcess:" + mIsMainProcess + " isCanReport:" + isCanReport + " key:" + key);
        if (isCanReport()) {
            MobclickAgent.onEventObject(this.mApplication, key, mEventMap);
        }
        mEventMap.clear();
    }

    /**
     * 自定义事件，参考OEM自定义事件设计 https://docs.qq.com/sheet/DYU1Ba3h6cHd6YXdU?tab=BB08J2
     * 自用类里面的eventMap，与putEventMap配合使用QuickTrackingUtil.putEventMap（）.onEventObject（）
     *
     * @param key 键
     */
    public void onEventObject(String key) {
        Log.d("QuickTrackingUtil", "onEventObject: key=" + key + ", mEventMap=" + mEventMap);
        if (mApplication == null) {
            return;
        }
        Log.i("buryEvent", "2 mIsMainProcess:" + mIsMainProcess + " isCanReport:" + isCanReport + " key:" + key);
        if (isCanReport()) {

            MobclickAgent.onEventObject(this.mApplication, key, mEventMap);

        }

        mEventMap.clear();
    }

    public Map<String, Object> copyMap(Map<String, Object> eventMap) {
        Map<String, Object> newMap = new HashMap<>();
        if (eventMap != null) {
            newMap.putAll(eventMap);
        }
        return newMap;
    }

    public void onEvent(String key) {
        if (mApplication == null) {
            return;
        }
        // 暂时没有地方调用，以后用的时候在加对应的aidl方法
        if (mIsMainProcess) {
            if (isCanReport()) {
                MobclickAgent.onEvent(this.mApplication, key);
            }
        }
    }

    /**
     * App内嵌H5嵌入
     */
    public void attachWebView(WebView webView) {
        H5Manager.enableJavaScriptBridge(webView);
        SpmAgent.attach(webView);
    }

    /**
     * App内嵌H5嵌入
     */
    public void attachX5WebView(View webView) {
        H5Manager.enableJavaScriptBridge(webView);
        SpmAgent.attachX5(webView);
    }

    /**
     * App内嵌H5移除
     */
    public void detachWebView() {
        SpmAgent.detach();
    }


    /**
     * 启动分析application attachBaseContext开始
     */
    public void attachBaseContextStart(Application app) {
        LaunchManager.onTraceApp(app, LaunchManager.APP_ATTACH_BASE_CONTEXT, true);
    }

    /**
     * 启动分析application attachBaseContext结束
     */
    public void attachBaseContextEnd(Application app) {
        LaunchManager.onTraceApp(app, LaunchManager.APP_ATTACH_BASE_CONTEXT, false);
    }

    /**
     * 启动分析application onCreate结束
     */
    public void onApplicationCreateEnd(Application app) {
        LaunchManager.onTraceApp(app, LaunchManager.APP_ON_CREATE, false);
    }

    /**
     * 启动分析Activity的onCreate()方法开始
     */
    public void onActivityCreate(Activity activity) {
        LaunchManager.onTracePage(activity, LaunchManager.PAGE_ON_CREATE, true);
    }

    /**
     * 启动分析 Activity的onRestart()方法开始
     */
    public void onActivityRestart(Activity activity) {
        LaunchManager.onTracePage(activity, LaunchManager.PAGE_ON_RE_START, true);
    }

    /**
     * 启动分析Activity的onStart()方法开始
     */
    public void onActivityStart(Activity activity) {
        LaunchManager.onTracePage(activity, LaunchManager.PAGE_ON_START, true);
    }

    /**
     * 启动分析Activity的onResume()方法开始
     */
    public void onActivityResume(Activity activity) {
        LaunchManager.onTracePage(activity, LaunchManager.PAGE_ON_RESUME, false);
    }

    /**
     * 启动分析Activity的onStop()方法开始
     */
    public void onActivityStop(Activity activity) {
        LaunchManager.onTracePage(activity, LaunchManager.PAGE_ON_STOP, true);
    }
}
