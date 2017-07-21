package com.example.dengshaomin.coderecycleview;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CommonAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.HeaderAndFooterWrapper;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycleView;
    CommonAdapter<String> commonAdapter;
    List<String> datas = new ArrayList<>();
    HeaderAndFooterWrapper<String> headerAndFooterWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, getResources
                ().getColor(R.color.cardview_dark_background), 1));
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        if (commonAdapter == null) {
            commonAdapter = new CommonAdapter<String>(MainActivity.this, R.layout.item_view, datas) {
                @Override
                protected void convert(ViewHolder holder, String s, int position) {
                    ((TextView) holder.getConvertView().findViewById(R.id.text)).setText(datas.get
                            (position));
                }
            };
        }
        headerAndFooterWrapper = new HeaderAndFooterWrapper<>(commonAdapter);
        initHeaderFoot();
        recycleView.setAdapter(headerAndFooterWrapper);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (datas == null) datas = new ArrayList<String>();
                for (int i = 0; i < 50; i++) {
                    datas.add(i + "");
                }
                headerAndFooterWrapper.notifyDataSetChanged();
            }
        }, 3000);
    }


    private void initHeaderFoot() {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(10, 10, 10, 10);
        textView.setText("header1");
        TextView textView1 = new TextView(this);
        textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        textView1.setPadding(10, 10, 10, 10);
        textView1.setText("foot1");
        headerAndFooterWrapper.addHeaderView(textView);
        headerAndFooterWrapper.addFootView(textView1);
    }
}
