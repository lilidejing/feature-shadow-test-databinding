package com.kye.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Routing
 * Desc 公共的协程相关的工具方法
 * Source
 * Created by yb on 2018/10/25 15:06
 * Modify by yb on 2018/10/25 15:06
 * Version 1.0
 */
object CoroutineUtils {

    /**
     * 该方法用于在异步操作，然后同步返回
     */
    fun <T> doInBackgroundAndReturn(action: () -> T): T {
        return runBlocking {
            val job = async(Dispatchers.IO) {
                action()
            }
            job.await()
        }
    }
}