package com.tencent.shadow.sample.host.plugin_view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tencent.shadow.sample.host.R;
import com.tencent.shadow.sample.host.adapter.MyFragmentPagerAdapter;
import com.tencent.shadow.sample.host.fragment.MyTestFragment;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder;

import java.util.ArrayList;

public class HostAddPluginViewActivity extends AppCompatActivity implements HostAddPluginViewContainer {
    private ViewGroup mPluginViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置下主题，否则会崩溃
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);

        //测试加载插件fragment ---- 测试不通过
//        setContentView(R.layout.layout_fragment_activity);
//        loadPluginFragment();

        //测试加载插件fragment ---- 测试通过
//        setContentView(R.layout.layout_fragment_activity2);
//        loadPluginFragment();

        //测试加载插件 viewPager + fragment ---- 测试通过
        setContentView(R.layout.layout_fragment_activity3);
        loadPluginFragment();


        //测试加载插件View
//        LinearLayout activityContentView = new LinearLayout(this);
//        activityContentView.setOrientation(LinearLayout.VERTICAL);
//
//        LinearLayout.LayoutParams wrapContent = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//
//        TextView note = new TextView(this);
//        note.setLayoutParams(wrapContent);
//        note.setText("需要先启动插件sample-plugin-app后，才能点下面的加载插件View");
//
//        Button loadButton = new Button(this);
//        loadButton.setText("加载插件View");
//        loadButton.setOnClickListener(this::loadPluginView);
//        loadButton.setLayoutParams(wrapContent);
//
//        ViewGroup pluginViewContainer = new LinearLayout(this);
//        pluginViewContainer.setLayoutParams(wrapContent);
//        mPluginViewContainer = pluginViewContainer;
//
//        View[] views = {
//                note,
//                loadButton,
//                pluginViewContainer
//        };
//        for (View view : views) {
//            activityContentView.addView(view);
//        }
//        setContentView(activityContentView);
    }

    private void loadPluginView(View view) {
        //简化逻辑，只允许点一次
        view.setEnabled(false);

        //因为当前Activity和插件都在:plugin进程，不能直接操作主进程的manager对象，所以通过一个广播调用manager。
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction("sample_host.manager.startPluginService");

        final int id = System.identityHashCode(this);
        HostAddPluginViewContainerHolder.instances.put(id, this);
        intent.putExtra("id", id);

        sendBroadcast(intent);
    }

    private void loadPluginFragment() {
        //因为当前Activity和插件都在:plugin进程，不能直接操作主进程的manager对象，所以通过一个广播调用manager。
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction("sample_host.manager.startPluginService");

        final int id = System.identityHashCode(this);
        HostAddPluginViewContainerHolder.instances.put(id, this);
        intent.putExtra("id", id);

        sendBroadcast(intent);
    }


    @Override
    public void addView(View view) {
        mPluginViewContainer.addView(view);
    }

    @Override
    public void addView(View view1, View view2) {
        MyTestFragment myTestFragment1 = MyTestFragment.newInstance(new Bundle());
        MyTestFragment myTestFragment2 = MyTestFragment.newInstance(new Bundle());
        myTestFragment1.setView(view1);
        myTestFragment2.setView(view2);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container1, myTestFragment1).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container2, myTestFragment2).commit();
    }

    @Override
    public void addFragment(Fragment fragment) {
        Log.e("LCF", "classLoad1111 = " + this.getClass().getClassLoader());
        Log.e("LCF", "classLoad2222 = " + fragment.getClass().getClassLoader());
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void addFragmentView(View view1, View view2) {
        ViewPager viewPager = findViewById(R.id.view_pager);
        MyTestFragment myTestFragment1 = MyTestFragment.newInstance(new Bundle());
        myTestFragment1.setView(view1);

        MyTestFragment myTestFragment2 = MyTestFragment.newInstance(new Bundle());
        myTestFragment2.setView(view2);

        ArrayList<MyTestFragment> fragmentList = new ArrayList<>();
        fragmentList.add(myTestFragment1);
        fragmentList.add(myTestFragment2);


        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }

}
