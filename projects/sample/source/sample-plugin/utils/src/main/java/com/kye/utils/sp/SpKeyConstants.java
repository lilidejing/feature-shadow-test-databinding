package com.kye.utils.sp;

/**
 * Routing
 * Desc    Sp键常量
 * Source
 * Created by LWS on 2018/7/12 10:33
 * Version 1.0
 */

public interface SpKeyConstants {

    // api模块专用
    interface Api {
        String KEY_USER = "KEY_USER";

        //------longonresponse------//
        String DEPARTMENT = "department";//
        String SESSION_ID = "sessionID";//
        String USER_ID = "userID";//
        String DEVICE_ID = "deviceID";//
        String APP_VER = "appVer";// app version app版本
        String USER_NAME = "userName";//
        String USER_PWD = "userPWD";//
        String USER_DEPART = "userDepart";//
        String DEVICE_KND = "deviceKND";//
        String DOMAIN_NA = "domainNa";//
        String USE_RPOPDOM_SW = "userPopdomSw";//
        String SERVER_TIME = "serverTime";//
        String S_VR_VER = "sVRVer";//
        String TOKEN = "token";//
        String POINT_NAME = "pointName";//
        String ZHI_CHUN = "ZhiChun";//
        String LAST_UPLOAD_TIME = "lastUploadTime";//
        String DP110 = "DP110";//

        String IS_SWITCH = "IsSwitch";//
        String PERSON_SW = "PersonSW";//
        String TELEPHONE = "Telephone";
        String TELE_MOBILE = "TeleMobile";
        String IS_COMPANY = "IsCompany";
        String IS_HIDESEAL = "isHideseal";
        String IDENTITY_NAME = "IdentityName";
        String UPDATE_REC_COUNT = "updateRecCount";//

        String WP_SIGN_TIME = "wpSignTime";    // 木架登记共同作业人员数
        String IS_HIDE_JD = "isHideJD";
        String NEW_OR_OLD = "NewOrOld";
        String IS_OLD = "isOld";
        String LOADING_SW = "loadingSW";
        String USER_PERMISSION = "userPermission";
        String MODULE_LAST_VERSION = "moduleLastVersion"; // 通用模块最后加载版本
        String IS_AUTO_SCHEDULE_PERSON = "IsAutoSchedulePerson"; //是否是自动调度
        String NEXT_CERTIFICATION_XDNO = "nextCertificationXDNo"; //上一次实名认证并报单的下单编码
        String IS_SELECT_STATION = "isSelectStation"; //是否选择了指定站点
        String COO_ALL_DEPT_NEAR = "All_DeptNear"; //COO所有停靠中转场
        String TEST_FLAG = "testFlag"; //COO所有停靠中转场

        //------NetWork------//
        String CHECK_NET_RADIO = "checkNetRadio";
        String LOGIN_NETWORK = "login_network"; //
        String LOGIN_NETWORK_VERSION = "login_network_version"; //
        String CHECK_RADIO = "checkRadio"; //

        String URL = "newurl";//

        //------Open API------//
        String OPEN_API_URL = "openApiUrl";//
        String OPEN_TOKEN = "open_token";//

        String SPLASH_DATA_JSON = "splash_data_json";//

        /*时间*/
        String LOGIN_TIME = "loginTime";// 登录服务器时间
        String CURRENT_TIME = "currentTime"; //登录时的本地时间
        String POSITION_CONFIG = "positionConfig"; //经纬度获取模块配置开发
    }


    /**
     * 其他的sp
     */
    interface Other {
        String ADDRESS_BEAN_LATELY_USE = "LatelyUseAddress"; //最近使用的地址
        String HISTORY_PICK_UP = "historyPickUp"; //历史带货
        String DEVICE_PHONE = "device_phone"; //本设备电话记录，用于虚拟号绑定
    }


}
