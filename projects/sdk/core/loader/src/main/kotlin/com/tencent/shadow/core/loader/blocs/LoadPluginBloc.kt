/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tencent.shadow.core.loader.blocs

import android.content.Context
import android.os.Process
import android.util.Log
import com.tencent.shadow.core.common.InstalledApk
import com.tencent.shadow.core.load_parameters.LoadParameters
import com.tencent.shadow.core.loader.exceptions.LoadPluginException
import com.tencent.shadow.core.loader.infos.PluginParts
import com.tencent.shadow.core.loader.managers.ComponentManager
import com.tencent.shadow.core.loader.managers.PluginPackageManagerImpl
import com.tencent.shadow.core.runtime.PluginPartInfo
import com.tencent.shadow.core.runtime.PluginPartInfoManager
import com.tencent.shadow.core.runtime.ShadowAppComponentFactory
import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object LoadPluginBloc {
    @Throws(LoadPluginException::class)
    fun loadPlugin(
        executorService: ExecutorService,
        componentManager: ComponentManager,
        lock: ReentrantLock,
        pluginPartsMap: MutableMap<String, PluginParts>,
        hostAppContext: Context,
        installedApk: InstalledApk,
        loadParameters: LoadParameters
    ): Future<*> {
        println("lgj  准备获取PluginClassLoader   "+"  进程id  "+ Process.myPid())
        if (installedApk.apkFilePath == null) {
            throw LoadPluginException("apkFilePath==null")
        } else {
            Log.e("LCF", "##############")
            val buildClassLoader = executorService.submit(Callable {
                lock.withLock {
                    //加载插件
                    Log.e("LCF", "000000===yeyeyeye")
                    Log.e("LCF", "pluginPartsMap yeyeyeye = " + pluginPartsMap.toString())
                    LoadApkBloc.loadPlugin(installedApk, loadParameters, pluginPartsMap)
                }
            })

            val buildPluginManifest = executorService.submit(Callable {
                Log.e("LCF", "111111")
                val pluginClassLoader = buildClassLoader.get()
                Log.e("LCF", "====buildPluginManifest===pluginClassLoader==$pluginClassLoader")
                Log.e("LCF", "222222")
                // 解析插件manifest
                val pluginManifest = pluginClassLoader.loadPluginManifest()
                // 检查插件和宿主包名一致
                CheckPackageNameBloc.check(pluginManifest, hostAppContext)
                pluginManifest
            })

            val buildPluginApplicationInfo = executorService.submit(Callable {
                Log.e("LCF", "333333")
                val pluginManifest = buildPluginManifest.get()
                Log.e("LCF", "444444")
                //  初始化ApplicationInfo
                val pluginApplicationInfo = CreatePluginApplicationInfoBloc.create(
                    installedApk,
                    loadParameters,
                    pluginManifest,
                    hostAppContext
                )
                pluginApplicationInfo
            })

            val buildPackageManager = executorService.submit(Callable {
                Log.e("LCF", "555555")
                val pluginApplicationInfo = buildPluginApplicationInfo.get()
                Log.e("LCF", "666666")
                val hostPackageManager = hostAppContext.packageManager
                // 通过宿主context获PackageManager并封装相关信息类
                PluginPackageManagerImpl(
                    pluginApplicationInfo,
                    installedApk.apkFilePath,
                    componentManager,
                    hostPackageManager,
                )
            })

            val buildResources = executorService.submit(Callable {
                Log.e("LCF", "777777")
                // 创建插件Resource
                CreateResourceBloc.create(installedApk.apkFilePath, hostAppContext)
            })

            // 封装组件信息
            val buildAppComponentFactory = executorService.submit(Callable {
                Log.e("LCF", "888888")
                val pluginClassLoader = buildClassLoader.get()
                Log.e("LCF", "====buildAppComponentFactory===pluginClassLoader==$pluginClassLoader")
                Log.e("LCF", "999999")
                val pluginManifest = buildPluginManifest.get()
                Log.e("LCF", "100000")
                val appComponentFactory = pluginManifest.appComponentFactory
                if (appComponentFactory != null) {
                    val clazz = pluginClassLoader.loadClass(appComponentFactory)
                    ShadowAppComponentFactory::class.java.cast(clazz.newInstance())
                } else ShadowAppComponentFactory()
            })

            // 初始化插件ShadowApplication，它是插件Application替换的普通父类
            val buildApplication = executorService.submit(Callable {
                Log.e("LCF", "11-11-11")
                val pluginClassLoader = buildClassLoader.get()
                Log.e("LCF", "12-12-12===buildApplication pluginClassLoader==$pluginClassLoader")
                val resources = buildResources.get()
                Log.e("LCF", "13-13-13")
                val appComponentFactory = buildAppComponentFactory.get()
                val pluginManifest = buildPluginManifest.get()
                val pluginApplicationInfo = buildPluginApplicationInfo.get()

                CreateApplicationBloc.createShadowApplication(
                    pluginClassLoader,
                    loadParameters,
                    pluginManifest,
                    resources,
                    hostAppContext,
                    componentManager,
                    pluginApplicationInfo,
                    appComponentFactory
                )
            })

            val buildRunningPlugin = executorService.submit {
                if (File(installedApk.apkFilePath).exists().not()) {
                    throw LoadPluginException("插件文件不存在.pluginFile==" + installedApk.apkFilePath)
                }
                Log.e("LCF", "14-14-14")
                val pluginPackageManager = buildPackageManager.get()
                val pluginClassLoader = buildClassLoader.get()
                Log.e("LCF", "====buildRunningPlugin===pluginClassLoader==$pluginClassLoader")
                val resources = buildResources.get()
                val shadowApplication = buildApplication.get()
                val appComponentFactory = buildAppComponentFactory.get()
                val pluginManifest = buildPluginManifest.get()
                Log.e("LCF", "15-15-15")
                lock.withLock {
                    Log.e("LCF", "16-16-16")
                    componentManager.addPluginApkInfo(
                        pluginManifest,
                        loadParameters,
                        installedApk.apkFilePath,
                    )
                    Log.e("LCF", "pluginPartsMap2222222 = " + pluginPartsMap.toString())
                    pluginPartsMap[loadParameters.partKey] = PluginParts(
                        appComponentFactory,
                        shadowApplication,
                        pluginClassLoader,
                        resources,
                        pluginPackageManager
                    )

                    Log.d("lgj", "pluginPartsMap ${loadParameters.partKey} 存放ClassLoader相关 $pluginClassLoader")

                    PluginPartInfoManager.addPluginInfo(
                        pluginClassLoader, PluginPartInfo(
                            shadowApplication, resources,
                            pluginClassLoader, pluginPackageManager
                        )
                    )
                }
            }

            return buildRunningPlugin
        }
    }


}