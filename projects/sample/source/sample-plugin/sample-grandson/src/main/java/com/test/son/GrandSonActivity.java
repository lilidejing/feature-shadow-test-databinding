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

package com.test.son;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.tencent.shadow.sample.plugin.app.lib.base.plugin.grand.R;
//import com.tencent.shadow.sample.plugin.app.lib.gallery.AppCompatTestActivity;
//import com.tencent.shadow.sample.plugin.app.lib.gallery.MainActivity;
//import com.tencent.shadow.sample.plugin.app.lib.gallery.splash.SplashActivity;
//import com.tencent.shadow.sample.plugin.app.lib.gallery.util.ToastUtil;
import com.test.son.tools.QtCollectionReport;

import java.util.ArrayList;
import java.util.List;

public class GrandSonActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PluginAppThemeLight2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_skip3);
        setTitle("GrandSonActivity跳转接收");
        Button skip = findViewById(R.id.button);
        skip.setOnClickListener(v -> {

            // GrandSon 插件中

//            Intent intent = new Intent(this, SplashActivity.class);
//            startActivity(intent);


//            ToastUtil.showToast(this, "公共业务的工具类");

            QtCollectionReport.putClickEvent("GrandSonActivity跳转接收", "插件孙子");

            QtCollectionReport.downloadTimeEventReport(
                    "abc", 200000, 2000, "成功", ""
            );
        });

    }

}
