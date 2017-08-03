package com.example.dengshaomin.coderecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CodeRecycleView;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CodeRecyclerViewFooter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CommonAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WithValayoutActivity extends AppCompatActivity {
    CodeRecycleView recyclerView;
    CommonAdapter<String> commonAdapter;
    List<String> datas = new ArrayList<>();
    private VirtualLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_valayout);
        recyclerView = (CodeRecycleView) findViewById(R.id.coderecycleView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, getResources
                ().getColor(R.color.cardview_dark_background), 1));
        layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<LayoutHelper> helpers = new LinkedList<>();

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setItemCount(25);


        final ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_RIGHT, 100, 100);

        helpers.add(DefaultLayoutHelper.newHelper(7));
        helpers.add(scrollFixLayoutHelper);
        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);

        layoutManager.setLayoutHelpers(helpers);

        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(getAdapter());
        headerAndFooterWrapper.addFootView(new CodeRecyclerViewFooter(this));
        recyclerView.setAdapter(headerAndFooterWrapper);
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerView.scrollToPosition(7);
//                recyclerView.getAdapter().notifyDataSetChanged();
//            }
//        }, 6000);

    }

    private VirtualLayoutAdapter getAdapter() {
        return new VirtualLayoutAdapter(layoutManager) {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(new TextView(WithValayoutActivity.this));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 300);
                holder.itemView.setLayoutParams(layoutParams);

                ((TextView) holder.itemView).setText(Integer.toString(position));

                if (position == 7) {
                    layoutParams.height = 60;
                    layoutParams.width = 60;
                } else if (position > 35) {
                    layoutParams.height = 200 + (position - 30) * 100;
                }

                if (position > 35) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 30) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa00ff00);
                } else {
                    holder.itemView.setBackgroundColor(0xccff00ff);
                }
            }

            @Override
            public int getItemCount() {
                List<LayoutHelper> helpers = getLayoutHelpers();
                if (helpers == null) {
                    return 0;
                }
                int count = 0;
                for (int i = 0, size = helpers.size(); i < size; i++) {
                    count += helpers.get(i).getItemCount();
                }
                return count;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.reset:
                break;
            case R.id.all:
                break;
            case R.id.no_springback:
                break;
            case R.id.refresh_only:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
