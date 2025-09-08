package com.tencent.test.second;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.tencent.shadow.sample.plugin.app.lib.base.second.R;


/**
 * @author LCF
 * Description:
 * @date : 2025/5/21
 */
public class MyTestActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_test_second);
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
