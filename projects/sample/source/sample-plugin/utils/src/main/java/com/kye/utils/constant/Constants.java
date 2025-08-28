package com.kye.utils.constant;

/**
 * Routing
 * Desc    常量类
 * Source
 * Created by LWS on 2018/7/26 17:43
 * Version 1.0
 */

public class Constants {
    /**
     * 上一票大货需扫描流动卡板进行绑定
     */
    public static final int FLOW_PALLET_BIND_TIPS = 300040115;
    /**
     * 上一票大货需扫描流动卡板进行确认
     */
    public static final int FLOW_PALLET_CONFIRMED_TIPS = 300040116;

    public interface ToolBarViewItem {

        int TOOLBAR_CENTER_TV = 0;
        int TOOLBAR_RIGHT_TV = 1;
        int TOOLBAR_LEFT_TV = 2;
    }

    /**
     * 文件夹的命名
     */
    public interface FileFolderName {

        String PDA_ROOT_FILE_NAME = "pda";
        String PDA_CRASH = "crashlog";
        String PDA_NORMAL_LOG = "normallog";
        String PDA_BURY_LOG = "burylog";
        String PDA_PUSH_LOG = "pushlog";
        // 宿主xlog文件夹，里面还有xlog缓存文件夹
        String PDA_HOST_XLOG = "hostXLog";
        String PDA_INTER_FOUNDATION_XLOG = "interXLogFoundation";
        String PDA_INTER_OTHER_XLOG = "interXLogOther";
        String PDA_ALLOCATE_HIGH_XLOG = "allocateXLogHigh";
        String PDA_ALLOCATE_MID_XLOG = "allocateXLogMid";
        String PDA_ALLOCATE_LOW_XLOG = "allocateXLogLow";
        String PDA_TAKE_HIGH_XLOG = "takeXLogHigh";
        String PDA_TAKE_MID_XLOG = "takeXLogMid";
        String PDA_TAKE_LOW_XLOG = "takeXLogLow";

        /**
         * 文件异步上传、文件下载进程的xLog文件夹
         */
        String PDA_FILE_XLOG = "fileXLog";
        // 跨声进程的xLog文件夹
        String PDA_KUA_SHENG_XLOG = "kuaShengXLog";
        String OLD_PDA_ROOT_FILE_NAME = "KYSD";
        String PDA_HOTFIX = "hotfix";
        String PDA_PHOTO_UPLOAD_LOG = "photolog";
        String PHONE_RECORD = "PhoneRecord";
        String PHONE_RECORD_6C = "CallRecord";
        String PHONE_RECORD_6B = "CallRecord";
        String PDA_KY_CACHE = "ky-cache";
        String PDA_KY_CONFIG = "ky-config";
        String PDA_ELECTRONIC_RECEIPT = "ElectronicReceipt";
        String PDA_MMKV = "mmkv";
        /**
         * 宿主APP下载目录
         */
        String PDA_HOST_APP = "hostApp";
        /**
         * 插件下载目录
         */
        String PDA_PLUGIN_DOWNLOAD = "PluginDownload";
        /**
         * PDA目录下的数据库文件存储目录
         */
        String PDA_DATABASE = "databases";
    }

    /**
     * SharedPreferences的Key值
     */
    public interface SharedPreferenceKey {
        /**
         * 悬浮框不再提醒
         */
        String FLOAT_NO_REMIND_CHECKED = " float_no_remind_check";
        //蓝牙称名称
        String BLUE_SCALE_NAME = "blue_scale_name";
        //蓝牙称mac
        String BLUE_SCALE_MAC = "blue_scale_mac";
        //打印机2名称
        String PRINTER_NAME2 = "printer_name2";
        //打印机2mac
        String PRINTER_MAC2 = "printer_mac2";
        //打印机名称
        String PRINTER_NAME = "printer_name";
        //打印机mac
        String PRINTER_MAC = "printer_mac";
        //变焦备注
        String ZOOM_LEVEL = "zoom_level";

    }

    /**
     * 建包解包常量
     */
    public interface PackUnpackKey {
        String UPDATE_SCANNED_UN_SCANNED_WAYBILL_TAG = "update_scanned_un_scanned_waybill_tag";
        String UPDATE_SCANNED_UN_SCANNED_WAYBILL = "update_scanned_un_scanned_waybill";
        String UPDATE_REMOVE_SCANNED_WAYBILL = "update_remove_scanned_waybill";
    }

    /*
     * 挑货弹窗类型
     * */
    public interface PickUpGoodsWindowType {
        //服务方式
        int SERVICE_TYPE = 0x01;
        //重量
        int WEIGHT = 0x02;
        //派送方式
        int DELIVERY_STYLE = 0x03;
        //运输方式
        int TRANSPORT_STYLE = 0x04;
        //加工作日
        int ADD_WORK_DAY = 0x05;
        //目的大区
        int DESTINATION_AREA = 0x06;
        //已添加航班号
        int ADDED_FLIGHT_NUMBER = 0x07;
        //已添运单号
        int ADDED_WAYBILL_NUMBER = 0x08;
        //始发分拨
        int START_DELIVERY = 0x09;
        //目的分拨
        int DESTINATION_DELIVERY = 0x10;
        //目的机场
        int DESTINATION_AIRPORT = 0x11;
        //请求code
        int PICKUP_GOODS_REQUEST = 0x11;
        //结果code
        int PICKUP_GOODS_RESULT = 0x12;

        String PICKUP_GOODS_DATA_KEY = "pickup_goods_data_key";
        String PICKUP_GOODS_DATA_INTENT_VALUE = "pickup_goods_data_intent_value";
        String PICKUP_GOODS_DATA_INTENT_WIDTH = "pickup_goods_data_intent_width";
        String PICKUP_GOODS_DATA_INTENT_HEIGHT = "pickup_goods_data_intent_height";
        String PICKUP_GOODS_DATA_INTENT_KEY_BOARD_HEIGHT = "pickup_goods_data_intent_key_board_height";

        String PICKUP_GOODS_DATA_ACTION_TO_CHANGE_POSITION = "pickup_goods_data_action_to_change_position";
        String PICKUP_GOODS_DATA_ACTION_TO_CLEAR_DATA = "pickup_goods_data_action_to_clear_data";
    }

    /**
     * 干线任务状态
     */
    public interface TrunkLineTaskStatus {
        int COMPLETE = 10; //完成
        int NO_OUT_CAR = 70; //只有签到时间，为未发车状态
        int NO_SIGN_IN = 60; //没有签到时间，为未签到状态，不考虑有发车未签到
    }

    /**
     * 蓝牙的搜索类型
     */
    public interface BluetoothSearchType {

        /**
         * 蓝牙4.0以下
         */
        int SEARCH_TYPE_CLASSIC = 1;

        /**
         * 蓝牙4.0及以上（BLE）
         */
        int SEARCH_TYPE_BLE = 2;

    }

    public interface ErrorCode {
        /**
         * 需复方
         */
        int NEED_WEIGHT_FU_FANG = 30003001;
        /**
         * 需复磅
         */
        int NEED_WEIGHT_FU_BAND = 30003002;
        /**
         * 需复磅复方
         */
        int NEED_WEIGHT_FU_BAND_FU_FANG = 30003003;
        /**
         * 货物无尺寸，请先复方
         */
        int NEED_COMPOUND_ERROR_CODE = 30003005;
        /**
         * 请扫描运单号
         */
        int ERROR_CODE_30004001 = 30004001;
        /**
         * AIRPORT_TRANSPORT_MODEL_MODEL_TIP(300040113,"运输方式为陆运，请核实是否用机场发件"
         */
        int ERROR_CODE_300040113 = 300040113;
        /**
         * 请补打标签
         */
        int ERROR_CODE_30004002 = 30004002;
        /**
         * 不可装车
         */
        int ERROR_CODE_304003 = 304003;
        /**
         * 超重提示(非上海地区)
         */
        int ERROR_CODE_30003004 = 30003004;
        /**
         * 危险品超重
         */
        int ERROR_CODE_304004 = 304004;
        /**
         * 危险品需上报
         */
        int ERROR_CODE_30004007 = 30004007;
        /**
         * 场地内存在相同客户运单%s票，请注意带齐
         */
        int ERROR_CODE_300040099 = 300040099;

        /**
         * 场地内存在相同客户运单，是否退出
         */
        int ERROR_CODE_300040100 = 300040100;
        /**
         * 已调度》该运单已安排%s，请确认是否继续上传
         */
        int ERROR_CODE_300040131 = 300040131;
        /**
         * 查询不到配货或代理信息，请联系配载
         */
        int ERROR_CODE_300303 = 300303;
        /**
         * 转代理货物，请确认是否装车
         */
        int TRANSFER_AGENT_GOODS_CONFIRM_LOADING = 34131;
        /**
         * 转代理货物，不可带往点部
         */
        int TRANSFER_AGENT_GOODS_INTERCEPT = 34132;
        /**
         * 电话预约
         */
        int ERROR_CODE_300040130 = 300040130;

        int ERROR_CODE_32311 = 32311;
        /**
         * 特殊线路重量小于20KG，请确认是否继续上传
         */
        int ERROR_CODE_300040120 = 300040120;
    }

    public interface QuickTrackingKey {
        String PDA_OPERATE_LOG = "pda_operate_log";
        String MODULE = "module";
        String BIZ_CODE = "biz_code";
        String OPERATE_TYPE = "operate_type";
        String OPERATE_CONTENT = "operate_content";
        String OPERATE_TICK = "operate_tick";
    }

}
