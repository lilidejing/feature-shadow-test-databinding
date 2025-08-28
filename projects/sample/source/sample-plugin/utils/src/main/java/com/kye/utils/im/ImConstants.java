package com.kye.utils.im;

/**
 * @author LCF
 * Description: IM相关常量
 * @date : 2024/9/11
 */
public class ImConstants {

    public interface Kye {
        /**
         * 营地-未读数
         */
        String KYE_IM_CAMP_UNREAD_COUNT = "kye_im_camp_unread_count";
    }

    /**
     * IM 来源
     */
    public interface ImSource {
        /**
         * 客户端发送的消息，传 0
         */
        int CLIENT_TRIGGER_POINTS = 0;
        /**
         * 服务端推送的消息，传 1
         */
        int SERVER_TRIGGER_POINTS = 1;
    }

    /**
     * 取 : 业务类型范围: 1001 ~ 1999
     */
    public interface PickUpBizType {
        /**
         * 快速揽件未扫数量
         */
        int BIZ_TYPE_QUICK_COLLECT_PARCEL_UN_SCAN_COUNT = 1002;

        /**
         * AI检测结果提醒
         */
        int BIZ_TYPE_AI_CHECK_RESULT_TIP = 1003;

        /**
         * 取货任务重量变更过大语音提醒
         */
        int BIZ_WEIGH_CHANGE_OVERSIZE = 1004;

        /**
         * 取货调度推送外请整车司机到达提醒
         */
        int BIZ_HIRED_DRIVER_ARRIVED = 1005;

        /**
         * 运单修改地址提醒
         */
        int BIZ_UPDATE_ADDRESS_REMIND = 1006;

        /**
         * 取货延时
         */
        int PICKUP_DELAY_CHANGE = 1007;
        /**
         * 取货时效
         */
        int PICKUP_LIMIT_TIME = 1008;
        /**
         * 取货任务他人完成
         */
        int PICKUP_TASK_COMPLETE = 1009;
        /**
         * 未报单驶离增加清除签到提醒
         */
        int UN_REPORT_DRIVER_OFF_CLEAR_SIGN = 1010;

        /**
         * 白单取货任务报单提醒
         */
        int BLANK_REPORT_REMIND = 1011;
        /**
         * 取货调度新增任务
         */
        int PICKUP_DISPATCH_ADD_TASK = 1012;
        /**
         * 取派时效变更
         */
        int PICK_UP_TASK_PRESCRIPTION_PERIOD_CHANGE = 1013;
        /**
         * 整车修改货好提醒
         */
        int COMPLETE_VEHICLE_CHANGE_GOODS_TIME = 1014;
        /**
         * 客户扫码认证结果
         */
        int SCAN_AUTHENTICATE_RESULT = 1019;
    }

    /**
     * 派 : 业务类型范围: 2001 ~ 2999
     */
    public interface DeliveryBizType {

        /**
         * 派货-运单修改地址提醒
         */

        int BIZ_UPDATE_ADDRESS_REMIND = 2001;

        /**
         * 派货任务超时提醒
         */
        int BIZ_TASK_OVER_TIME_REMIND = 2002;

        /**
         * 派货任务退拦单提醒
         */
        int BIZ_TASK_RETURN_WAYBILL_NOTICE_REMIND = 2003;

        /**
         * 派货调度新增任务通知
         */
        int DELIVERY_DISPATCH_ADD_TASK = 2004;
        /**
         * 派货调度任务取消通知
         */
        int DELIVERY_DISPATCH_TASK_CHANGE = 2005;

        /**
         * 修改货好时间审核未通过语音提醒
         */
        int DISPATCH_PICKUP_FAIL_AUDIT_SMS = 1018;

        /**
         * 取货任务货好时间已变更
         */
        int PUSH_TAKE_TASK_GOOD_TIME_CHANGE = 1017;


        /**
         * 调度修改任务
         */
        int DISPATCH_UPDATE_TASK = 1016;

        /**
         * 调度修改任务
         */
        int DISPATCH_TAKE_GOODS_CANCEL = 1015;
        /**
         * 未报单驶离增加清除派货签到提醒
         */
        int PUSH_FAILURE_REPORT_DEPARTURE_NOTICE = 2006;
        /**
         * 取消走货工单审核未通过语音提醒
         */
        int PUSH_CANCEL_TRANSPORT_GOODS_ORDER_VERIFY_FAIL = 1020;
    }

    /**
     * 中转 : 业务类型范围: 3001 ~ 3999
     */
    public interface AllocateBizType {

    }

    /**
     * 综合 : 业务类型范围: 4001 ~ 4999
     */
    public interface ComprehensiveBizType {
        /**
         * 采集开关(对应推送code：231)
         */
        int BIZ_TYPE_CONFIG_GLOBAL_CONFIG = 4001;
        /**
         * 处理埋点日志上报(对应推送code：400)
         */
        int BIZ_TYPE_UPLOAD_BURY_LOG = 4002;
        /**
         * 处理人员缓存数据(对应推送code：500)
         */
        int BIZ_TYPE_UPDATE_WORKER_CACHE = 4003;
        /**
         * 更新登录信息（对应推送code：521）
         */
        int BIZ_TYPE_UPDATE_LOGIN_INFO = 4004;
        /**
         * 清理缓存(对应推送code：520)
         */
        int BIZ_TYPE_CLEAR_CACHE = 4005;
        /**
         * 巴枪区域-清除巴枪缓存(对应推送code：670)
         */
        int BIZ_TYPE_BLEND_CLEAR_PDA_CACHE = 4006;
        /**
         * 评价处理通知(对应推送code：523)
         */
        int BIZ_TYPE_BLEND_MODULE_COMMENT_DEAL = 4007;
        /**
         * 常用数据字典推送
         */
        int BIZ_TYPE_BLEND_LOOKUP_DATA_DICT = 4008;
        /**
         * 小哥营地活动二次推送(对应推送code：690)
         */
        int BIZ_TYPE_CAMP_ACTIVITY_REMIND = 5002;

        /**
         * 支付成功
         */
        int PAYMENT_SUCCESSFUL = 4010;

    }

    /**
     * 小哥营地 : 业务类型范围:  5001 ~ 5999
     */
    public interface CampBizType {
        /**
         * 营地-未读数
         */
        int BIZ_TYPE_CAMP_UNREAD_COUNT = 5001;
    }

    /**
     * 其它 : 业务类型范围: 6007 ~ 6999
     */
    public interface OtherBizType {
        /**
         * 巴枪区域-H5离线管理-消息推送，推送给用户完成H5的升级
         */
        int AREA_H5_OFFLINE_MANAGE = 6007;

        /**
         * 功能使用上报
         */
        int BIZ_FUNCTION_USAGE_REPORT = 6008;
    }

    public interface ImAction {

        /**
         * 异常 登出
         */
        String ACTION_KYE_EXCEPTION_LOGOUT_BY_IM = "action_kye_exception_logout_by_im";

        /**
         * 发送：派货-派货列表-获取UI展示
         */
        String ACTION_DELIVERY_LIST_UI_SEND = "kye_im_delivery_list_ui_send";
        /**
         * 发送：拉取数据字典
         */
        String ACTION_PULL_LOOKUP_LIST_SEND = "kye_im_pull_lookup_list_send";
        /**
         * 接收：取货任务-取货任务列表其他信息
         */
        String ACTION_PICK_UP_LIST_OTHER_INFO_RECEIVE = "kye_im_pick_up_list_other_info_receive";

        /**
         * 接收：快速揽件未扫件数
         */
        String ACTION_QUICK_COLLECT_PARCEL_UN_SCAN_COUNT = "kye_im_quick_collect_parcel_un_scan_count";
        /**
         * 接收：客户扫码认证
         */
        String ACTION_PICKUP_REPORT_REAL_NAME_AUTH = "kye_im_pickup_report_real_name_auth";
        /**
         * 发送：功能使用情况上报
         */
        String ACTION_FUNCTION_USAGE_REPORT = "action_kye_function_usage_im";

    }

    public interface IntentExtra {
        /**
         * 具体业务的 IM数据
         */
        String IM_BUSINESS_DATA = "kye_im_business_data";
    }

    public interface ImLog {
        String IM_TAG = "IM 推送: ";
    }
}
