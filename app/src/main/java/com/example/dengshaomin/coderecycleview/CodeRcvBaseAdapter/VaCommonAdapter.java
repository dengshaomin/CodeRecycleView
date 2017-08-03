package com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class VaCommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public VaCommonAdapter(final Context context, @NonNull VirtualLayoutManager layoutManager, final int layoutId,
                           List<T> datas) {
        super(context, datas);
        mContext = context;
        this.mLayoutManager = layoutManager;
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
                VaCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

    @NonNull
    protected VirtualLayoutManager mLayoutManager;


    public void setLayoutHelpers(List<LayoutHelper> helpers) {
        this.mLayoutManager.setLayoutHelpers(helpers);
    }

    @NonNull
    public List<LayoutHelper> getLayoutHelpers() {
        return this.mLayoutManager.getLayoutHelpers();
    }
}
