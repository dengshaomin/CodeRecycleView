package com.example.dengshaomin.coderecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.CommonAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.ItemViewDelegate;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.MultiItemTypeAdapter;
import com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter.ViewHolder;

import java.util.List;

/**
 * Created by dengshaomin on 2017/8/2.
 */

public abstract class CodeVirtualLayoutAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    @NonNull
    protected VirtualLayoutManager mLayoutManager;

    public CodeVirtualLayoutAdapter(final Context context, @NonNull VirtualLayoutManager layoutManager, final int
            layoutId, List<T> datas) {
        super(context, datas);
        this.mLayoutManager = layoutManager;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CodeVirtualLayoutAdapter.this.convert(holder, t, position);
            }
        });
    }

    public void setLayoutHelpers(List<LayoutHelper> helpers) {
        this.mLayoutManager.setLayoutHelpers(helpers);
    }

    @NonNull
    public List<LayoutHelper> getLayoutHelpers() {
        return this.mLayoutManager.getLayoutHelpers();
    }


    protected abstract void convert(ViewHolder holder, T t, int position);
}
