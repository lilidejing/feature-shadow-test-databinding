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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.tencent.shadow.sample.plugin.app.lib.base.plugin.R;
import com.tencent.shadow.sample.plugin.app.lib.base.plugin.databinding.LayoutActivitySkip2Binding;
import com.test.tools.QtCollectionReport;

public class TestActivitySkip3 extends BasesActivity2 {

    private LayoutActivitySkip2Binding binding;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PluginAppThemeLight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_skip2);
        setTitle("Activity跳转接收");
        Button skip = findViewById(R.id.button);
        skip.setOnClickListener(v -> {
//            QtCollectionReport.putClickEvent("GrandSonActivity跳转接收", "插件孙子");
//
//            QtCollectionReport.downloadTimeEventReport(
//                    "abc", 200000, 2000, "成功", ""
//            );
        });
    }*/

    @Override
    public int getLayoutResId() {
        return R.layout.layout_activity_skip2;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

        /*binding.button.setOnClickListener(v -> {


        });*/
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initDataBinding() {
        binding = createViewDataBinding();

        Log.d("lgj"," mDataBinding = " + binding);
//        mDataBinding = createViewDataBinding(R.layout.activity_home);
//        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

    }


}
