package com.tencent.shadow.sample.plugin.app.lib.usecases.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.shadow.sample.plugin.app.lib.R;


/**
 * 简单item view
 */
public class SimpleItemView extends LinearLayout {
    private static final String TAG = "SimpleItemView";
    private Context mContext;
    private TextView tVtitle;

    public SimpleItemView(Context context) {
        this(context, null);
    }

    public SimpleItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SimpleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mContext = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_simple_item_view, this,
                true);
        RelativeLayout layout = rootView.findViewById(R.id.main_content);
        tVtitle = rootView.findViewById(R.id.item_title);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了item", Toast.LENGTH_SHORT).show();
                tVtitle.setText(mContext.getResources().getString(R.string.plugin_view_title2));
            }
        });
    }

    public void setTextColor(int color){
        tVtitle.setTextColor(color);
    }

    public void setText(String text){
        tVtitle.setText(text);
    }

}
