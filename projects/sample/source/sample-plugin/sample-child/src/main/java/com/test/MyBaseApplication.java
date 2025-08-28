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

package com.test;

import android.app.Application;
import android.util.Log;

import com.kye.pda.burypoint.QuickTrackingUtil;

public class MyBaseApplication extends Application {

    private static MyBaseApplication sInstence;

    public boolean isOnCreate;

    @Override
    public void onCreate() {
        sInstence = this;
        isOnCreate = true;
        super.onCreate();


        // 友盟QT初始化
        try {
            QuickTrackingUtil.getInstance()
                    .preMainInit(this, true);
            Log.d("lgj", "【启动器】 友盟已经预初始化");

            QuickTrackingUtil.getInstance().onApplicationCreateEnd(sInstence);

        } catch (QuickTrackingUtil.UMInitException e) {
            e.printStackTrace();
        }

        try {
            // 初始化埋点
            QuickTrackingUtil.getInstance().init(true);
            // 埋点开启页面自动采集 、QT相关 异步线程 9
            QuickTrackingUtil.getInstance().checkAutoOrManual(false);
            QuickTrackingUtil.getInstance().onProfileSignIn("0001111111222");
            Log.d("lgj", "【启动器】 友盟已经初始化");
        } catch (QuickTrackingUtil.UMInitException e) {
            Log.d("lgj", "初始化异常" + e.getMessage());
            e.printStackTrace();

        }
    }

    public static MyBaseApplication getInstance() {
        return sInstence;
    }
}
