package com.kye.utils.sp

import com.kye.pda.utils.fieldstore.IFieldStore
import com.kye.pda.utils.fieldstore.sp.SPFieldStoreImp


/**
 * @author :liuHanXiang
 * Description: 字段存储管理类
 * @date : 2024/4/17
 */
object KYFieldStoreManager {
    private lateinit var fieldStoreImp: IFieldStore
    private val spFieldStoreInstanceMap = HashMap<String, IFieldStore>()
    // 用于解决跨进程使用同name sp数据丢失的问题，理论上不同的进程使用不同的sp name，不要试图使用sp来跨进程通信
    private var spNamePrefix = ""
    fun init(spNamePrefix: String) {
        this.spNamePrefix = spNamePrefix
        // 在这里决定使用哪种存储策略
        this.fieldStoreImp = SPFieldStoreImp("$spNamePrefix$SP_NAME_KYE_SP")
    }

    /**
     * 【开发者在此维护字段存储实例的name】
     *  备注：如果使用sharedPreference，宿主和插件因为shadow框架的原因初始化sp实例的时候如果同名则会共享sp文件，但是并不能实现
     *  进程间数据同步，因为在get数据的时候还是在各自内存里的实例sp去获取,应用重启后才能拿到正确的值！！
     *  【字段存储工具不建议作为进程间数据共享的手段！！】
     */
    // 自动干线模块专用sp
    private const val SP_NAME_AUTO_MODULE = "autoModuleLibValue"

    // FloatLogoMenu专用sp
    private const val SP_NAME_FLOAT_LOGO = "floatLogo"

    // 之前SPUtils的默认sp实例
    private const val SP_NAME_SP_UTILS = "spUtils"

    //记录打印运单的模版类型选择
    const val SP_NAME_KYE_BLUETOOTH_TEMPLATE = "kye_sp_bluetooth_template"
    private const val SP_NAME_LOGIN = "spLogin"
    private const val SP_NAME_KYE_SP = "kye_sp"

    /**
     * 【开发者在此维护字段存储实例的获取方法】
     *  所有sp对应实例的方法入口收缩到此，避免业务代码随意实例化sp
     */
    fun getDefaultFSInstance(): IFieldStore {
        return fieldStoreImp
    }

    fun getAutoModuleFSInstance(): IFieldStore {
        return getFSInstance(SP_NAME_AUTO_MODULE)
    }

    fun getFloatLogoFSInstance(): IFieldStore {
        return getFSInstance(SP_NAME_FLOAT_LOGO)
    }

    fun getSPUtilsFSInstance(): IFieldStore {
        return getFSInstance(SP_NAME_SP_UTILS)
    }

    fun getSPLoginFSInstance(): IFieldStore {
        return getFSInstance(SP_NAME_LOGIN)
    }

    /**
     * 蓝牙模版记忆sp实例获取
     */
    fun getBluetoothTemplateSPUtilsFSInstance(): IFieldStore {
        return getFSInstance(SP_NAME_KYE_BLUETOOTH_TEMPLATE)
    }
    private fun getFSInstance(spName: String): IFieldStore {
        val finalSpName = "$spNamePrefix$spName"
        var spFieldStoreInstance: IFieldStore? = spFieldStoreInstanceMap[finalSpName]
        if (spFieldStoreInstance == null) {
            synchronized(this) {
                spFieldStoreInstance = spFieldStoreInstanceMap[finalSpName]
                if (spFieldStoreInstance == null) {
                    spFieldStoreInstance = fieldStoreImp.getFSInstance(finalSpName)
                    spFieldStoreInstanceMap[finalSpName] = spFieldStoreInstance!!
                }
            }
        }
        return spFieldStoreInstance!!
    }
}