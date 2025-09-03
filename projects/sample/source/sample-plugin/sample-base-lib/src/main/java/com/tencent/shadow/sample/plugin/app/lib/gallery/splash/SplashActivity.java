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

package com.tencent.shadow.sample.plugin.app.lib.gallery.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tencent.shadow.sample.plugin.app.lib.base.R;
import com.tencent.shadow.sample.plugin.app.lib.base.databinding.LayoutSplashBinding;
import com.tencent.shadow.sample.plugin.app.lib.gallery.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private SplashAnimation mSplashAnimation;

    private LayoutSplashBinding binding; // 自动生成的 binding 类


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
//        setContentView(R.layout.layout_splash);
      // 然后用 DataBindingUtil.bind 绑定
        // 再绑定 DataBinding
//        binding = DataBindingUtil.bind(findViewById(android.R.id.content));

        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_splash, null, false);

        setContentView(binding.getRoot());

        // 使用 DataBinding 加载布局
//        binding = DataBindingUtil.setContentView(this, R.layout.layout_splash);

        mSplashAnimation = new SplashAnimation(this);
        mSplashAnimation.start();

        mSplashAnimation.setAnimationListener(new ISplashAnimation.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                finish();

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
    }
}
