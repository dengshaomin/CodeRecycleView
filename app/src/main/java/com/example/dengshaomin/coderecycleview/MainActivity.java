package com.example.dengshaomin.coderecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CodeRecycleView;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CommonAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CodeRecycleView coderecycleView;
    CommonAdapter<String> commonAdapter;
    List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coderecycleView = (CodeRecycleView) findViewById(R.id.coderecycleView);
        coderecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, getResources
                ().getColor(R.color.cardview_dark_background), 1));
        coderecycleView.setLayoutManager(new LinearLayoutManager(this));
        if (commonAdapter == null) {
            commonAdapter = new CommonAdapter<String>(MainActivity.this, R.layout.item_view, datas) {
                @Override
                protected void convert(ViewHolder holder, String s, int position) {
                    ((TextView) holder.getConvertView().findViewById(R.id.text)).setText(datas.get
                            (position));
                }
            };
        }
        coderecycleView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (datas == null) datas = new ArrayList<String>();
                        datas.clear();
                        for (int i = 0; i < 10; i++) {
                            datas.add(i + "");
                        }
                        coderecycleView.refreshComplete(CodeRecycleView.SUCCESS);
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence, final int index) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (datas == null) datas = new ArrayList<String>();
                        int size = datas.size();
                        for (int i = 0; i < (index == 3 ? 9 : 10); i++) {
                            datas.add((size + i) + "");
                        }
                        coderecycleView.refreshComplete(CodeRecycleView.SUCCESS);
                    }
                }, 2000);
            }
        });
        coderecycleView.setAdapter(commonAdapter);
        coderecycleView.addHeaderView(createHeaderView());
    }

    public View createHeaderView() {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(100, 100, 100, 100);
        textView.setText("header1");
        return textView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.reset:
                datas.clear();
                coderecycleView.reset();
                coderecycleView.addHeaderView(createHeaderView());
                coderecycleView.setRefreshMode(CodeRecycleView.BOTH);
                coderecycleView.setSpringBackMode(CodeRecycleView.BOTH);
                break;
            case R.id.all:
                coderecycleView.setRefreshMode(CodeRecycleView.BOTH);
                coderecycleView.setSpringBackMode(CodeRecycleView.BOTH);
                coderecycleView.setAutoRefresh(true);
                break;
            case R.id.no_springback:
                coderecycleView.setSpringBackMode(CodeRecycleView.NONE);
                break;
            case R.id.refresh_only:
                coderecycleView.setRefreshMode(CodeRecycleView.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }
}
