package com.smile.smilevideo.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smile.smilevideo.R;

/**
 * Created by yaojiulong on 2017/9/20.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    private ProgressDialog mDialog;
    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = initView(inflater,container);
        initFindViewById(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        setListener();
    }


    protected abstract View initView(LayoutInflater inflater, ViewGroup container);
    protected abstract void initFindViewById(View view);

    public void initData(){

    }

    public void setListener(){

    }

    public void showProgressDialog(){
        mDialog=ProgressDialog.show(getContext(),"提示","请稍等...",false);
    }

    public void dismssDialog(){
        if (mDialog!=null)
            mDialog.dismiss();
    }

    /**
     * 初始化下拉刷新layout
     * @param layout
     */
    public void initSwipeRefreshLayout(SwipeRefreshLayout layout){
        layout.setColorSchemeResources(R.color.refresh_color_1
                , R.color.refresh_color_2, R.color.refresh_color_3, R.color.refresh_color_4);
    }
}
