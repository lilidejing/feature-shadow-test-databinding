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

package com.tencent.shadow.sample.plugin.app.lib.usecases.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import androidx.databinding.DataBindingUtil;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.shadow.sample.plugin.app.lib.R;
//import com.tencent.shadow.sample.plugin.app.lib.databinding.LayoutActivityLifecycleBinding;
import com.tencent.shadow.sample.plugin.app.lib.gallery.cases.entity.UseCase;
import com.tencent.shadow.sample.plugin.app.lib.gallery.util.ToastUtil;
import com.tencent.shadow.sample.plugin.app.lib.tools.QtCollectionReport;

public class TestActivityOnCreate extends AppCompatActivity {

//    private LayoutActivityLifecycleBinding binding;

    public static class Case extends UseCase {
        @Override
        public String getName() {
            return "生命周期测试";
        }

        @Override
        public String getSummary() {
            return "测试Activity的生命周期方法是否正确回调";
        }

        @Override
        public Class getPageClass() {
            return TestActivityOnCreate.class;
        }
    }


  /*  @Override
    protected void attachBaseContext(Context newBase) {
        newBase.setTheme(R.style.PluginAppTheme);
        super.attachBaseContext(newBase);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.PluginAppTheme);
        String getThemeName =  getResources().getResourceEntryName(androidx.appcompat.R.style.Theme_AppCompat);
        Log.d("lgj","getThemeName=="+getThemeName);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        // 先 setContentView
        setContentView(R.layout.layout_activity_lifecycle);
        // 再绑定 DataBinding
//        binding = DataBindingUtil.bind(findViewById(android.R.id.content));
        // 然后用 DataBindingUtil.bind 绑定
//        binding = DataBindingUtil.bind(findViewById(R.id.root));

        ToastUtil.showToast(this, "onCreate");
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(TestActivityOnCreate.this, "com.tencent.test.MyTestActivity");
                startActivity(intent);

            }
        });

        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(TestActivityOnCreate.this, "com.tencent.test.MyTestActivity");
                startActivityForResult(intent, 10086);
            }
        });

        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(TestActivityOnCreate.this, "com.test.TestActivitySkip3");
                startActivity(intent);
            }
        });
        findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QtCollectionReport.downloadTimeEventReport(
                        "abc", 200000, 2000, "成功", ""
                );

                Intent intent = new Intent();
                intent.setClassName(TestActivityOnCreate.this, "com.tencent.shadow.sample.plugin.app.lib.gallery.second.MainActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 10086 && resultCode == 1998) {
            ToastUtil.showToast(this, "我是返回结果: " + data.getStringExtra("result"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ToastUtil.showToast(this, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ToastUtil.showToast(this, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtil.showToast(this, "onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ToastUtil.showToast(this, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ToastUtil.showToast(this, "onRestoreInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ToastUtil.showToast(this, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.showToast(this, "onDestroy");
    }
}
