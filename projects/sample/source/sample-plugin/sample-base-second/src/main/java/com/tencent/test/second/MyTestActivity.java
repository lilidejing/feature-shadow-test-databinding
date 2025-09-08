package com.tencent.test.second;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.tencent.shadow.sample.plugin.app.lib.base.plugin.second.R;
import com.tencent.shadow.sample.plugin.app.lib.base.plugin.second.databinding.LayoutActivityTestSecondBinding;


/**
 * @author LCF
 * Description:
 * @date : 2025/5/21
 */
public class MyTestActivity extends Activity {


    private LayoutActivityTestSecondBinding binding;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_test_second);
        binding = DataBindingUtil.bind(findViewById(R.id.root));

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
