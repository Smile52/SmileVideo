package com.smile.smilevideo.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smile.smilevideo.R;
import com.smile.smilevideo.base.BaseFragment;

/**全屏播放器
 * Created by yaojiulong on 2018/3/14.
 */

public class AllPlayerFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_all_player , container, false);
    }

    @Override
    protected void initFindViewById(View view) {

    }
}
