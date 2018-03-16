package com.smile.smilevideo.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.smile.smilevideo.R;
import com.smile.smilevideo.adapter.HomeAdapter;
import com.smile.smilevideo.base.BaseFragment;
import com.smile.smilevideo.config.Config;
import com.smile.smilevideo.entity.HomeEntity;
import com.smile.smilevideo.player.PlayerActivity;

import java.util.List;

/**首页
 * Created by yaojiulong on 2017/9/20.
 */

public class HomeFragment extends BaseFragment implements IHomeView{
    private HomePresenter mPresenter;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mContentView;
    private int mCount;
    private HomeAdapter mAdapter;
    private List<HomeEntity.ItemListBean> mDatas;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initFindViewById(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_refresh_layout);
        mContentView = (RecyclerView) view.findViewById(R.id.home_content_rlv);
        mContentView.setLayoutManager(new LinearLayoutManager(getContext()));
    }



    @Override
    public void initData() {
        super.initData();
        mPresenter = new HomePresenter(getContext(), this);
        mCount =1;
        Logger.e("德玛西亚");
        mPresenter.getSomeInfo(String.valueOf(mCount));
        initSwipeRefreshLayout(mRefreshLayout);
    }

    @Override
    public void setItemListData(List<HomeEntity.ItemListBean> listData) {
        this.mDatas= listData;
        mAdapter = new HomeAdapter(mDatas, getContext());
        mContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra(Config.INTENT_URL, mDatas.get(position).getData().getPlayUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setListener() {
        super.setListener();

    }
}
