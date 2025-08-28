package com.kye.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 *
 * Desc 跳转工具类
 * Source
 * Created by Geoff on 2021/8/10 10:04
 * Modify by Geoff on 2021/8/10 10:04
 * Version 1.0
 */
object IntentUtil {

    fun gotoActivity(context: Context, gotoClass: Class<*>) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        context.startActivity(intent)
    }

    fun gotoActivity(context: Context, gotoClass: Class<*>, bundle: Bundle) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    fun gotoActivityForResult(context: Context, gotoClass: Class<*>, requestCode: Int) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        (context as Activity).startActivityForResult(intent, requestCode)
    }

    fun gotoActivityForResult(
        context: Context,
        gotoClass: Class<*>,
        bundle: Bundle,
        requestCode: Int
    ) {
        val intent = Intent()
        intent.setClass(context, gotoClass)
        intent.putExtras(bundle)
        (context as Activity).startActivityForResult(intent, requestCode)
    }

}