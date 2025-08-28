package com.kye.pda.burypoint

/**
 * @author : 656950
 * @description :
 * @date : 2024/6/12
 */
class Constants {
    /**
     * 跳转时的Intent参数的Key值
     */
    interface QuickTrackingEvent {
        companion object {
            //交接扫描
            const val PAGE_HANDOVER_SCAN = "page_handover_scan"
            const val CLK_HANDOVER_SCAN = "clk_handover_scan"
            const val OTHER_HANDOVER_SCAN_COMMIT_INFO = "other_handover_scan_commit_info"
            const val PAGE_HANDOVER_SCAN_CHILD_MOTHER = "page_handover_scan_child_mother"
            const val PAGE_HANDOVER_SCAN_ROUTING_INFO = "page_handover_scan_routing_info"
            const val OTHER_HANDOVER_SCAN_ERROR = "other_handover_scan_error"
            const val OTHER_HANDOVER_SCAN_COMPLETE_TRANSITION =
                "other_handover_scan_complete_transition"
            const val PAGE_HANDOVER_SCAN_LACK_GOODS_CONFIRM =
                "page_handover_scan_lack_goods_confirm"
            const val OTHER_HANDOVER_SCAN_LACK_GOODS_COMFIRM =
                "other_handover_scan_lack_goods_comfirm"
            const val OTHER_NEED_KEKE_DISPATCH_COMFIRM = "other_need_keke_dispatch_comfirm"

            //取货
            const val PAGE_TAKE_GOODS_TASKS = "page_take_goods_tasks"
            const val OTHER_TAKE_GOODS_TASKS_SIGNIN = "other_take_goods_tasks_signIn"
            const val OTHER_TAKE_GOODS_TASKS_SIGN_TIPS = "other_take_goods_tasks_sign_tips"
            const val OTHER_TAKE_GOODS_TASKS_SIGN_SUCCESS = "other_take_goods_tasks_sign_success"
            const val PAGE_TAKE_GOODS_DETAIL = "page_take_goods_detail"
            const val CLK_TAKE_GOODS_DETAIL = "clk_take_goods_detail"
            const val OTHER_TAKE_GOODS_DETAIL_OUT_CAR = "other_take_goods_detail_out_car"
            const val OTHER_TAKE_GOODS_DETAIL_CONFIRMATION_CAR =
                "other_take_goods_detail_confirmation_car"
            const val OTHER_TAKE_GOODS_DETAIL_CHOSE_CAR = "other_take_goods_detail_chose_car"
            const val CLK_TAKE_GOODS_TASKS_PRINT_WAYBILL = "clk_take_goods_tasks_print_waybill"
            const val CLK_TAKE_GOODS_TASKS_PRINT_LABEL = "clk_take_goods_tasks_print_label"
            const val OTHER_TAKE_GOODS_TASKS_WAYBILL = "other_take_goods_tasks_waybill"
            const val PAGE_WAYBILL_FORM = "page_waybill_form"
            const val CLK_WAYBILL_FORM = "clk_waybill_form"
            const val OTHER_WAYBILL_FORM_SCAN_WAYBILL = "other_waybill_form_scan_waybill"
            const val OTHER_WAYBILL_FORM_SUBMIT = "other_waybill_form_submit"

            //派货
            const val PAGE_DELIVERY_GOODS = "page_delivery_goods"
            const val OTHER_DELIVERY_GOODS_SIGN = "other_delivery_goods_sign"
            const val OTHER_DELIVERY_GOODS_SIGNIN_TIPS = "other_delivery_goods_signIn_tips"
            const val OTHER_DELIVERY_GOODS_SIGN_SUCCESS = "other_delivery_goods_sign_success"
            const val OTHER_DELIVERY_GOODS_SCAN_RESULT = "other_delivery_goods_scan_result"
            const val PAGE_DELIVERY_GOODS_DETAIL = "page_delivery_goods_detail"
            const val CLK_DELIVERY_GOODS_DETAIL = "clk_delivery_goods_detail"
            const val CLK_DELIVERY_GOODS_PICTURE_GOODS = "clk_delivery_goods_picture_goods"
            const val OTHER_DELIVERY_GOODS_PICTURE_GOODS = "other_delivery_goods_picture_goods"
            const val OTHER_DELIVERY_GOODS_SIGN_BILL = "other_delivery_goods_sign_bill"
            const val PAGE_SIGN_FORM = "page_sign_form"
            const val CLK_SIGN_FORM = "clk_sign_form"
            const val OTHER_WAYBILL_FORM_COMMIT = "other_waybill_form_commit"

            //首页
            const val CLK_TAB_BOTTOM_TABS = "clk_tab_bottom_tabs"
            const val CLK_ITEM_CHILD = "clk_Item_child"
            const val OTHER_APP_SEARCH_NO_PERMISSION = "other_app_search_no_permission"
            const val OTHER_ERROR_TIPS = "other_error_tips"
            const val PAGE_MAIN = "page_main"
            const val PAGE_TASKS = "page_tasks"
            const val PAGE_SETTING = "page_setting"
            const val PAGE_APP = "page_app"

            //到件扫描
            const val PAGE_UNLOAD_GOODS = "page_unload_goods"
            const val OTHER_KEKE_DISCHARGE_CARGO_COMFIRM = "other_keke_discharge_cargo_comfirm"

            //通用
            const val CLK_EVENT = "other_modular_click"

            /**
             * 通用北极星埋点
             */
            const val CLK_AUTO_BUTTON = "clk_auto_button"

            /**
             * 通用_模块耗时数据采集
             */
            const val OTHER_MODULAR_OPERATION_TIME = "other_modular_operation_time"

            /**
             * 客户质量提醒埋点
             */
            const val KYE_QUALITY_INFO = "kye_quality_info"

            /**
             * 通用_接口请求详情
             */
            const val OTHER_API_REQUEST_DETAILS = "other_api_request_details"

            /**
             * 通用_用户操作异常采集
             */
            const val KYXG_EVENT_LOG = "kyxg_event_log"

            /**
             * 拨打电话
             */
            const val CALL_PHONE = "call_phone"

            /**
             * 跨越小哥卡顿丢帧日志
             */
            const val KYXG_BLOCK_LOG = "kyxg_block_log"
        }
    }


    interface QuickTrackingKey {
        companion object {
            //属性key
            const val BILL_NUMBER = "bill_number"
            const val PAGE_NAME = "page_name"
            const val ERROR_TEXT = "error_text"
            const val OPERATION_FLOW = "operation_flow"
            const val SUBMISSIONS_CONTENT = "submissions_content"
            const val WAYBILL_FORM_TIME = "waybill_form_time"
            const val REQUEST_RESULT = "request_result"
            const val TYPE_NAME = "type_name"
            const val CUSTOMER_CODE = "customer_code"
            const val ORDER_NUMBER = "order_number"
            const val BUTTON_NAME = "button_name"
            const val IS_BIND_CAR = "is_bind_car"
            const val REACHES_CUSTOMER = "reaches_customer"
            const val INTERACTIVE_SELECTION = "interactive_selection"
            const val IS_ABNORMAL_FEEDBACK = "is_abnormal_feedback"
            const val WAYBILL_NUMBER = "waybill_number"
            const val MODULE_NAME = "module_name"
            const val PRIMARY_CASSIFICATION = "primary_cassification"
            const val PEOPLE_NAME = "people_name"
            const val UPLOAD_PLACE = "upload_place"
            const val NETWORK_QUALITY_RESULT = "request_result"

            /**
             * 耗时类型
             */
            const val TYPE_TIME = "type_time"

            /**
             * 操作耗时
             */
            const val OPERATING_DURATION = "operating_duration"

            // 操作时间
            const val OPERATION_TIME = "operation_time"

            // 操作类型
            const val OPERATION_TYPE = "operation_type"

            // 操作id
            const val OPERATION_ID = "operation_id"

            // 子件编码
            const val SCAN_NUMBER = "scan_number"
            const val LOG_LEVEL = "log_level"
            const val LOG_TAG = "log_tag"
            const val MESSAGE = "message"
            const val CLASS_NAME = "class_name"

            /**
             * 指标名称
             */
            const val INDEX_NAME = "index_name"

            /**
             * 方法名
             */
            const val METHOD_NAME = "method_name"

            /**
             * 用于区分错误类型
             */
            const val LOG_SUB_TAG = "log_sub_tag"

            /**
             * 运单票数
             */
            const val WAYBILL_COUNT = "waybill_count"
            const val ERROR_TAG = "error_tag"
            const val ERROR_MESSAGE = "error_message"

            /**
             * 联系的角色(寄方、收方、销售、跟单、到达经理、其他) 数据字典：oem_call_phone_monitor_operate_event_type
             */
            const val CONTACT_ROLE_TYPE_CODE = "contact_role_type_code"

            /**
             * 电话操作按钮 数据字典：oem_call_phone_monitor_operate_event_type
             */
            const val OPERATE_EVENT_TYPE_CODE = "operate_event_type_code"

            /**
             * 虚拟id
             */
            const val VIRTUAL_ID = "virtualId"

            /**
             *  10 查件；20 取货列表；30 取货详情；40 派货列表;50 派货详情；1 其他；
             */
            const val MODULE_TYPE_CODE = "module_type_code"

            /**
             * 卡顿类型(type)
             */
            const val BLOCK_TYPE = "block_type"
            /**
             * 触发点时间(time)
             */
            const val TRIGGERING_TIME = "triggering_time"
            /**
             * 触发点类名(scene)
             */
            const val TRIGGERING_SCENE = "triggering_scene"
            /**
             * 进程名
             */
            const val PROCESS_NAME = "process_name"
            /**
             * 堆栈信息
             */
            const val THREAD_STACK = "thread_stack"
            /**
             * 当前App占用CPU百分比
             */
            const val CPU_APP = "cpu_app"
            /**
             * 当前系统可用内存大小
             */
            const val MEM_FREE = "mem_free"
            /**
             * Application 初始化耗时
             */
            const val APPLICATION_CREATE = "application_create"
            /**
             * 第一个 Activity 创建耗时
             */
            const val FIRST_ACTIVITY_CREATE = "first_activity_create"
            /**
             * 整体启动时间（从点击图标到首帧绘制完成）
             */
            const val STARTUP_DURATION = "startup_duration"
            /**
             * 是否为热启动（冷启动/热启动）
             */
            const val IS_WARM_START_UP = "is_warm_start_up"
            /**
             * 当前帧率
             */
            const val FPS = "fps"
            /**
             * 掉帧数(DROP_COUNT)
             */
            const val DROP_COUNT = "drop_count"

            /**
             * 屏幕刷新率（Hz）(REFRESH_RATE)
             */
            const val REFRESH_RATE = "refresh_rate"
            /**
             * 内容
             */
            const val CONTENT = "content"

        }
    }

    interface ComponentNameKey {
        companion object {
            const val pkg = "com.seuic.kysy"
            const val cls = "com.kye.pda.plugin.aidl.PluginHostService"
        }
    }

    interface QuickTrackingNum {
        companion object {
            const val KYE_1 = "1"
            const val KYE_10 = "10"
            const val KYE_20 = "20"
            const val KYE_30 = "30"
            const val KYE_40 = "40"
            const val KYE_50 = "50"
            const val KYE_60 = "60"
            const val KYE_70 = "70"
            const val KYE_80 = "80"
            const val KYE_510 = "510"
            const val KYE_520 = "520"
            const val KYE_530 = "530"
            const val KYE_540 = "540"
            const val KYE_550 = "550"
            const val KYE_560 = "560"
            const val KYE_570 = "570"
        }
    }
}