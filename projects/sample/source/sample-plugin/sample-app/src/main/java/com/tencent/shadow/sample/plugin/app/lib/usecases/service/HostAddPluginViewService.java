package com.tencent.shadow.sample.plugin.app.lib.usecases.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder;
import com.tencent.shadow.sample.plugin.app.lib.R;
import com.tencent.shadow.sample.plugin.app.lib.usecases.view.SimpleItemView;

public class HostAddPluginViewService extends IntentService {
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HostAddPluginViewService() {
        super("HostAddPluginViewService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int id = intent.getIntExtra("id", 0);
        HostAddPluginViewContainer viewContainer
                = HostAddPluginViewContainerHolder.instances.remove(id);

        uiHandler.post(() -> {

            //宿主加载插件fragment
            View view1 = LayoutInflater.from(this).inflate(
                    R.layout.layout_host_add_plugin_view, null, false);
            SimpleItemView itemView1 = view1.findViewById(R.id.itemView);
            itemView1.setTextColor(Color.parseColor("#1F55CF"));
            itemView1.setText("动态修改插件的文案");

            View view2 = LayoutInflater.from(this).inflate(
                    R.layout.layout_host_add_plugin_view, null, false);
            SimpleItemView itemView2 = view2.findViewById(R.id.itemView);
            itemView2.setTextColor(Color.parseColor("#CF3337"));
            itemView2.setText("动态修改插件的文案22222");
            viewContainer.addFragmentView(view1, view2);



            //宿主加载插件view
//            View view1 = LayoutInflater.from(this).inflate(
//                    R.layout.layout_host_add_plugin_view, null, false);
//            SimpleItemView itemView1 = view1.findViewById(R.id.itemView);
//            itemView1.setTextColor(Color.parseColor("#1F55CF"));
//            itemView1.setText("动态修改插件的文案");
//
//            View view2 = LayoutInflater.from(this).inflate(
//                    R.layout.layout_host_add_plugin_view, null, false);
//            SimpleItemView itemView2 = view2.findViewById(R.id.itemView);
//            itemView2.setTextColor(Color.parseColor("#CF3337"));
//            itemView2.setText("动态修改插件的文案22222");
//            viewContainer.addView(view1, view2);



            //宿主加载插件view
//            View view = LayoutInflater.from(this).inflate(
//                    R.layout.layout_host_add_plugin_view, null, false);
//            SimpleItemView itemView = view.findViewById(R.id.itemView);
//            itemView.setTextColor(Color.parseColor("#1F55CF"));
//            itemView.setText("动态修改插件的文案");
//            viewContainer.addView(view);



            //宿主加载插件fragment ----直接加载插件fragment目前不支持，因为classLoader不一致
//            String msg = "这是一个动态添加的fragment---222222";
//            Bundle bundle = new Bundle();
//            bundle.putString("msg", msg);
//            TestFragment testFragment = TestFragment.newInstance(bundle);
//            viewContainer.addFragment(testFragment);
        });
    }
}
