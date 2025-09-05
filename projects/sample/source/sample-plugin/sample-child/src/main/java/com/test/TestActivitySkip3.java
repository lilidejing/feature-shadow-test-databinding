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

import androidx.databinding.DataBindingUtil;

import com.tencent.shadow.sample.plugin.app.lib.base.plugin.R;
import com.tencent.shadow.sample.plugin.app.lib.base.plugin.databinding.LayoutActivitySkip2Binding;

public class TestActivitySkip3 extends Activity {

    private LayoutActivitySkip2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PluginAppThemeLight);
        super.onCreate(savedInstanceState);

//第一种使用databinding方式：============== 先 setContentView ,再绑定 DataBinding 这种方式会报错：java.lang.RuntimeException: Unable to start activity ComponentInfo{com.tencent.shadow.sample.host/com.tencent.shadow.sample.plugin.runtime.PluginDefaultProxyActivity}: java.lang.RuntimeException: java.lang.IllegalArgumentException: View is not a binding layout. Tag: layout/layout_activity_skip2_0===========================
        setContentView(R.layout.layout_activity_skip2);
        setTitle("Activity跳转接收");
        binding = DataBindingUtil.bind(findViewById(R.id.root));

//第二种使用databinding方式：=======  用这种方式，获取到的binding为  null  ===========================
//        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_activity_skip2, null, false);
//        setContentView(binding.getRoot());

//第三种使用databinding方式：======================用下面的方式不会报错,正常加载界面==。但是获取到的binding为  null======================
//        binding = DataBindingUtil.setContentView(this, R.layout.layout_activity_skip2);

    }


}
