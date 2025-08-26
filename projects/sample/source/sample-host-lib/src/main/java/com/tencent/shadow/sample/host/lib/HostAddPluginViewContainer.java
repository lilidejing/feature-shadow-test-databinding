package com.tencent.shadow.sample.host.lib;

import android.app.Fragment;
import android.view.View;

public interface HostAddPluginViewContainer {
    void addView(View view);
    void addView(View view1, View view2);
    void addFragment(Fragment fragment);
    void addFragmentView(View view1, View view2);
}
