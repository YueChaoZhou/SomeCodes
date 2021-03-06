package com.bb.module_common.base;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bb.module_common.R;


/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/27
 * Time: 23:41
 */
public abstract class ListFragment<A extends RecyclerView.Adapter> extends BaseFragment {

    private RecyclerView mRecyclerView;
    protected A mAdapter;
    private LinearLayoutManager mLayoutManager;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mRecyclerView = view.findViewById(R.id.rv_list);
        mAdapter = createAdapter();
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    protected abstract A createAdapter();

    protected void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }
}
