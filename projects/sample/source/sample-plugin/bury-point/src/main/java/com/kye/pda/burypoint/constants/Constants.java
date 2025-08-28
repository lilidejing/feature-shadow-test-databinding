package com.kye.pda.burypoint.constants;

/**
 * @author lgj
 * @description: 变量名称
 * @date : 2024/5/14 9:27
 */
public class Constants {

    /**
     * 取派插件变量名称
     */
    public static final String TAKE_DELIVERY_PLUGIN = ":takedeliveryplugin";

    /**
     * 中转插件变量名称
     */
    public static final String ALLOCATE_PLUGIN = ":allocateplugin";

    /**
     * 取派插件安装包名称
     */
    public static final String TAKE_DELIVERY_NAME = "plugin-take-delivery.zip";
    /**
     * 中转插件安装包名称
     */
    public static final String TAKE_ALLOCATE = "plugin-allocate.zip";

    /**
     * 取派插件下载的安装包缓存名称
     */
    public static final String TAKE_DELIVERY_NAME_TMP = "plugin-take-delivery.zip.bak";
    /**
     * 中转插件下载的安装包缓存名称
     */
    public static final String TAKE_ALLOCATE_TMP = "plugin-allocate.zip.bak";


    /**
     * 插件默认名称
     */
    public static final String PLUGIN_DEFAULT_NAME = "plugin_default.zip";
    /**
     * 宿主默认名称
     */
    public static final String HOST_DEFAULT_NAME = "host_default.apk";
    /**
     * 插件默认所在迭代
     */
    public static final String PLUGIN_DEFAULT_ITERATION_DATA = "00.0101";

    /**
     * 取派插件解压目录
     */
    public static final String UN_PACKED_TAKE_PLUGIN = "/ShadowPluginManager/UnpackedPlugin/TakeDeliveryPluginManager";
    /**
     * 中转插件解压目录
     */
    public static final String UN_PACKED_ALLOCATE_PLUGIN = "/ShadowPluginManager/UnpackedPlugin/DispatchPluginManager";
    /**
     * 数据库目录
     */
    public static final String DATABASE_PATH = "/databases";


    /**
     * 插件的类型值,取派插件
     */
    public static final int PLUGIN_TAKE_DELIVERY = 11;
    /**
     * 插件的类型值，中转插件
     */
    public static final int PLUGIN_ALLOCATE = 21;
    /**
     * 插件的类型值，宿主
     */
    public static final int TYPE_HOST = 1;

    /**
     * 插件的类型值，差分包（目前后端数据字典未定义，随便搞一个，后续定义了改下）
     */
    public static final int TYPE_PATCH = -10086;

    /**
     * 是否启动过取派插件
     */
    public static final String TAKE_PLUGIN_STARTED = "take_plugin_started";

    /**
     * 是否启动过分拨插件
     */
    public static final String ALLOCATE_PLUGIN_STARTED = "allocate_plugin_started";

    /**
     * 取派插件的patchId保存的sp的Key
     */
    public static final String PLUGIN_TAKEDELIVERY_PATCHID = "plugin_takedelivery_patchid";
    /**
     * 中转插件的patchId保存的sp的Key
     */
    public static final String PLUGIN_ALLOCATE_PATCHID = "plugin_allocate_patchid";
    /**
     * 上一个取派插件安装版本sp的Key
     */
    public static final String PLUGIN_TAKEDELIVERY_VERSION_OLD = "plugin_takedelivery_version_old";
    /**
     * 上一个中转插件安装版本sp的Key
     */
    public static final String PLUGIN_ALLOCATE_VERSION_OLD = "plugin_allocate_version_old";

    /**
     * 当前待安装包信息缓存数据sp的Key
     */
    public static final String PRE_INSTALL_INFO = "pre_install_info";
    /**
     * 静默安装安装的宿主更新信息
     */
    public static final String SILENCE_UPDATE_INFO = "silence_update_info";


    /**
     * 通知插件自杀
     */
    public static final String ACTION_KILL_SELF = "action_kill_self";

    /**
     * 插件进程名intent的key
     */
    public static final String INTENT_KEY_PLUGIN_NAME = "intent_key_plugin_name";


    //===============================友盟埋点的下载相关参数的Key值============================
    /**
     * 应用文件下载耗时
     */
    public static final String OTHER_DOWNLOAD_OPERATION_TIME = "other_download_operation_time";

    /**
     * （应用文件下载耗时埋点）下载文件类型
     */
    public static final String DOWNLOAD_FILE_TYPE = "download_file_type";
    /**
     * （应用文件下载耗时埋点）下载文件大小
     */
    public static final String DOWNLOAD_FILE_SIZE = "download_file_size";
    /**
     * （应用文件下载耗时埋点）下载文件类型
     */
    public static final String DOWNLOAD_DURATION = "download_duration";
    /**
     * （应用文件下载耗时埋点）文件平均下载速度
     */
    public static final String DOWNLOAD_SPEED = "download_speed";
    /**
     * （应用文件下载耗时埋点）下载结果
     */
    public static final String DOWNLOAD_RESULT = "download_result";
    /**
     * （应用文件下载耗时埋点）下载失败原因
     */
    public static final String DOWNLOAD_FAILED_REASON = "download_failed_reason";


    /**
     * （操作点击埋点）模块名称
     */
    public static final String MODULE_NAME = "module_name";
    /**
     * （操作点击埋点）按钮名称
     */
    public static final String BUTTON_NAME = "button_name";

    /**
     * （操作点击埋点）点击事件
     */
    public static final String CLK_EVENT = "other_modular_click";


    /**
     * 插件安装或启动耗时
     */
    public static final String OTHER_INSTALL_START_OPERATION_TIME = "other_install_start_operation_time";

    /**
     * 插件安装或启动耗时类型。复制、安装、启动、检测并安装综合基础插件、检测并安装综合基础插件并启动1PX、检测安装非综合基础插件
     */
    public static final String OTHER_INSTALL_START_OPERATION_TYPE = "other_install_start_operation_type";
    /**
     * （插件安装或启动耗时埋点）操作耗时
     */
    public static final String INSTALL_START_DURATION = "install_start_duration";
    /**
     * （插件安装或启动耗时埋点）插件类型
     */
    public static final String INSTALL_START_PLUGIN_TYPE = "install_start_plugin_type";
    /**
     * （插件安装或启动耗时埋点）插件个数
     */
    public static final String INSTALL_START_PLUGIN_NUM = "install_start_plugin_num";
    /**
     * （插件安装或启动耗时埋点）结果
     */
    public static final String INSTALL_START_PLUGIN_RESULT = "install_start_plugin_result";
    /**
     * （插件安装或启动耗时埋点）失败原因
     */
    public static final String INSTALL_START_PLUGIN_FAILED_REASON = "install_start_plugin_failed_reason";
    /**
     * （插件安装或启动耗时埋点）其他备注
     */
    public static final String INSTALL_START_PLUGIN_REMARK = "install_start_plugin_remark";



    //===============================友盟埋点的下载相关参数的Key值============================

}
