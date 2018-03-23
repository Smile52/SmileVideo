package com.smile.smilevideo.hot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smile.smilevideo.R;
import com.smile.smilevideo.base.BaseFragment;

/**热门
 * Created by yaojiulong on 2017/9/20.
 */

public class HotFragment extends BaseFragment{
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_hot, container, false);

    }

    @Override
    protected void initFindViewById(View view) {

    }
}
