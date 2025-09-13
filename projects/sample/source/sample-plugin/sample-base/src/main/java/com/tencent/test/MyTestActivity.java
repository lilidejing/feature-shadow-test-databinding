package com.tencent.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kye.pda.burypoint.QuickTrackingUtil;
import com.kye.pda.burypoint.constants.Constants;

/**
 * @author LCF
 * Description:
 * @date : 2025/5/21
 */
public class MyTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.tencent.shadow.sample.plugin.app.lib.base.R.layout.layout_activity_test);
        findViewById(com.tencent.shadow.sample.plugin.app.lib.base.R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QuickTrackingUtil.getInstance()
                        .putEventMap(Constants.BUTTON_NAME, "测试按钮")
                        .putEventMap(Constants.MODULE_NAME, "公共业务插件")
                        .onEventObject(Constants.CLK_EVENT);

                Intent intent = new Intent();
                intent.putExtra("result", "hello world");
                setResult(1998, intent);
                finish();
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
