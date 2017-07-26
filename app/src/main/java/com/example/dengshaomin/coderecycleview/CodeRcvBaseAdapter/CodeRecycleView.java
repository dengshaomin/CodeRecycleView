package com.example.dengshaomin.coderecycleview.CodeRcvBaseAdapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

import com.andview.refreshview.XRefreshView;
import com.example.dengshaomin.coderecycleview.GCLinearlayout;
import com.example.dengshaomin.coderecycleview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengshaomin on 2017/7/24.
 */

public class CodeRecycleView extends GCLinearlayout {

    public static final int START = 0;
    public static final int END = 1;
    public static final int BOTH = 2;
    public static final int NONE = 3;

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;
    private static final int DEFAULT = -1;
    private static final int WhitchPositionAutoShowLoadMoreFootView = 2;    //2代表倒数第几个

    private int refreshState = DEFAULT;
    private XRefreshView xRefreshView;
    private RecyclerView recycleView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private int pageSize = 10;
    private int pageIndex = 1;
    private View footView;
    private XRefreshView.XRefreshViewListener xRefreshViewListener;
    private int refreMode = BOTH;

    public CodeRecycleView(Context context) {
        super(context);
    }

    public CodeRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.code_recycle_view;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        recycleView.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recycleView.addItemDecoration(decor);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        this.layoutManager = layoutManager;
        recycleView.setLayoutManager(layout);
    }

    public void setSpringBackTime(int times) {
        xRefreshView.setPinnedTime(times);
    }

    public void setAutoRefresh(boolean autoRefresh) {
        xRefreshView.setAutoRefresh(autoRefresh);
    }

    public void setSpringBackMode(int mode) {
        switch (mode) {
            case START:
                xRefreshView.enableRecyclerViewPullDown(true);
                xRefreshView.enableRecyclerViewPullUp(false);
                xRefreshView.setPullLoadEnable(false);
                break;
            case END:
                xRefreshView.enableRecyclerViewPullUp(true);
                xRefreshView.enableRecyclerViewPullDown(false);
                break;
            case BOTH:
                xRefreshView.enableRecyclerViewPullUp(true);
                xRefreshView.enableRecyclerViewPullDown(true);
                break;
            case NONE:
                xRefreshView.enableRecyclerViewPullUp(false);
                xRefreshView.enableRecyclerViewPullDown(false);

                break;
        }
    }

    public void setRefreshMode(int mode) {
        refreMode = mode;
        switch (mode) {
            case START:
                xRefreshView.setPullRefreshEnable(true);
                xRefreshView.setPullLoadEnable(false);
                break;
            case END:
                xRefreshView.setPullRefreshEnable(false);
                xRefreshView.setPullLoadEnable(true);
                break;
            case BOTH:
                xRefreshView.setPullRefreshEnable(true);
                xRefreshView.setPullLoadEnable(false);
                break;
            case NONE:
                xRefreshView.setPullRefreshEnable(false);
                xRefreshView.setPullLoadEnable(false);
                break;
        }
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        xRefreshView.setAutoLoadMore(autoLoadMore);
    }

    public void setXRefreshViewListener(final XRefreshView.XRefreshViewListener l) {
        xRefreshViewListener = l;
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                pageIndex = 1;
                refreshState = START;
                if (l != null) {
                    l.onRefresh(isPullDown);
                }
            }

            @Override
            public void onLoadMore(boolean isSilence, int index) {
                needLoadMore();

            }
        });
    }

    private void needLoadMore() {
        if (refreMode == START || refreMode == NONE || refreshState == END) return;
        if (adapter != null && adapter instanceof HeaderAndFooterWrapper) {
            if (footView == null) {
                footView = new CodeRecyclerViewFooter(getmContext());
                ((HeaderAndFooterWrapper) adapter).addFootView(footView);
                adapter.notifyDataSetChanged();
//                recycleView.scrollBy(0, 20);
            }
//            HeaderAndFooterWrapper tempada = ((HeaderAndFooterWrapper) adapter);
//            int count = tempada.getItemCount() - tempada.getHeadersCount() - tempada.getFootersCount();
//            if(count)
//        }
            int realyCount = 0;
            if (adapter != null) {
                int count = adapter.getItemCount();
                if (adapter instanceof HeaderAndFooterWrapper) {
                    count = count - ((HeaderAndFooterWrapper) adapter).getHeadersCount() - ((HeaderAndFooterWrapper) adapter)
                            .getFootersCount();
                }
                realyCount = count;
                if (count % pageSize != 0) {
                    return;
                }
            }
            this.pageIndex = realyCount / pageSize + 1;
            refreshState = END;
            if (xRefreshViewListener != null) {
                xRefreshViewListener.onLoadMore(true, this.pageIndex);
            }
        }
    }

    public void refreshComplete(int state) {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (refreshState == START) {
            xRefreshView.stopRefresh();
        } else if (refreshState == END) {
            xRefreshView.stopLoadMore();
        }
        refreshState = DEFAULT;
        switch (state) {
            case SUCCESS:
                break;
            case ERROR:
                break;
            case EMPTY:
                break;
        }
    }

    @Override
    public void initView() {
        xRefreshView = (XRefreshView) getRootView().findViewById(R.id.xRefreshView);
        recycleView = (RecyclerView) getRootView().findViewById(R.id.recycleView);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setAutoLoadMore(true);
        //设置静默加载时提前加载的item个数
//        xefreshView1.setPreLoadCount(4);
        //设置Recyclerview的滑动监听
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
//		// 实现Recyclerview的滚动监听，在这里可以自己处理到达底部加载更多的操作，可以不实现onLoadMore方法，更加自由
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (dx > 0 && isScrollBottom()) {
                    needLoadMore();
                }
            }

            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isScrollBottom()) {
                        needLoadMore();
                    }
                }
            }
        });
    }

    private boolean isScrollBottom() {
        //recyclerView.canScrollVertically(1) //是否滑动到最底部
        //recyclerView.canScrollVertically(-1)  //是否滑动到最顶部
        int realyCount = 0;
        if (adapter instanceof HeaderAndFooterWrapper) {
            realyCount = adapter.getItemCount() - ((HeaderAndFooterWrapper) adapter).getHeadersCount() - (
                    (HeaderAndFooterWrapper)
                            adapter)
                    .getFootersCount();
        } else {
            realyCount = adapter.getItemCount();
        }
        if (layoutManager instanceof LinearLayoutManager) {
            return realyCount - WhitchPositionAutoShowLoadMoreFootView <= ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            return realyCount - WhitchPositionAutoShowLoadMoreFootView <= ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] into = new int[0];
            ((StaggeredGridLayoutManager)
                    layoutManager).findLastVisibleItemPositions(into);
            if (into != null) {
                return realyCount - WhitchPositionAutoShowLoadMoreFootView <= into[into.length - 1];
            } else {
                return true;
            }
        } else {
            return true;
        }
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

    }

}
