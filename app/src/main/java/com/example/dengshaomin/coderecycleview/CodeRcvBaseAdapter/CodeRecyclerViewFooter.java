package com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dengshaomin.coderecycleview.GCLinearlayout;
import com.example.dengshaomin.coderecycleview.R;

import java.util.List;

/**
 * Created by dengshaomin on 2017/7/25.
 */

public class CodeRecyclerViewFooter extends GCLinearlayout {
    private View progress, no_more_tip;

    public CodeRecyclerViewFooter(Context context) {
        super(context);
    }

    public CodeRecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeRecyclerViewFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.coderecyclerview_foot_view;
    }

    @Override
    public void initView() {
        progress = findViewById(R.id.progress);
        no_more_tip = findViewById(R.id.no_more_tip);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void getNetData() {

    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void EventComming(String indentify, Object data) {

    }

    @Override
    public void setViewData(Object data) {
        if (data instanceof Boolean && !(Boolean) data) {
            no_more_tip.setVisibility(GONE);
            progress.setVisibility(VISIBLE);
        } else {
            no_more_tip.setVisibility(VISIBLE);
            progress.setVisibility(GONE);
        }
    }
}
