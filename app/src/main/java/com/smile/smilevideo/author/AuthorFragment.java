package com.smile.smilevideo.author;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smile.smilevideo.R;
import com.smile.smilevideo.base.BaseFragment;

/**作者页面
 * Created by yaojiulong on 2017/9/20.
 */

public class AuthorFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_author, container, false);
    }

    @Override
    protected void initFindViewById(View view) {

    }
}
