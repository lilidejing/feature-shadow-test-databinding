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
import com.tencent.shadow.sample.plugin.app.lib.gallery.MainActivity;

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
            Intent intent = new Intent();
            intent.setClassName(GrandSonActivity.this.getPackageName(),
                    "com.tencent.shadow.sample.plugin.app.lib.gallery.MainActivity");
            startActivity(intent);



        });

        /*List<Integer> resList = new ArrayList<>();
        List<Bitmap> bitmapList = new ArrayList<>();
        resList.add(R.mipmap.pexels_arthur_shuraev_67501761_33528753);
        resList.add(R.mipmap.pexels_arthur_shuraev_67501761_33528753_2);
        resList.add(R.mipmap.pexels_gemilang_malang_travel_2154995644_33543277);
        resList.add(R.mipmap.pexels_gemilang_malang_travel_2154995644_33543277_2);
        resList.add(R.mipmap.pexels_gemilang_malang_travel_2154995644_33543277_22);
        resList.add(R.mipmap.pexels_sandro_tedeschini_694018589_18071152);
        resList.add(R.mipmap.pexels_sandro_tedeschini_694018589_18071152_2);
        resList.add(R.mipmap.pexels_tiago_chaves_2154478168_33463350);
        resList.add(R.mipmap.pexels_tiago_chaves_2154478168_33463350_2);
        resList.add(R.mipmap.pexels_yasar_baskurt_706180077_33516862);
        resList.add(R.mipmap.pexels_yasar_baskurt_706180077_33516862_2);
        resList.add(R.mipmap.pexels_yasar_dbaskurt_706180077_33516862_2);
        resList.add(R.mipmap.p1);
        resList.add(R.mipmap.p12);
        resList.add(R.mipmap.p1231);
        resList.add(R.mipmap.p121211);
        resList.add(R.mipmap.p1333);
        resList.add(R.mipmap.p1344);
        resList.add(R.mipmap.p13232);
        resList.add(R.mipmap.p13323);
        resList.add(R.mipmap.p133234);
        resList.add(R.mipmap.p144553);
        resList.add(R.mipmap.p143);
        resList.add(R.mipmap.p1555);
        resList.add(R.mipmap.p1567);
        for (int i = 0; i < resList.size(); i++){
            Log.d("lgj",resList.get(i).toString());
            Bitmap b = BitmapFactory.decodeResource(getResources(), resList.get(i));
            bitmapList.add(b);
        }*/
        /*for (int i = 0; i < bitmapList.size(); i++){
            Log.d("lgj",bitmapList.get(i).toString());
        }*/

    }

}
