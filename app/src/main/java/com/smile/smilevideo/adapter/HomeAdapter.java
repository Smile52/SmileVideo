package com.smile.smilevideo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smile.smilevideo.R;
import com.smile.smilevideo.entity.HomeEntity;

import java.util.List;

/**
 * Created by yaojiulong on 2017/9/26.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeEntity.ItemListBean, BaseViewHolder> {
    private Context mContext;
    public HomeAdapter(@Nullable List<HomeEntity.ItemListBean> data, Context context) {

        super(R.layout.item_video_layout, data);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity.ItemListBean item) {
        if (item.getData().getCover()==null){
            return;
        }
        HomeEntity.ItemListBean.DataBean dataBean =item.getData();
        Log.e("smile", " test  "+dataBean.getCategory()+"title    "+dataBean.getTitle());
        if (dataBean.getCover().getFeed() == null){
            Glide.with(mContext).load(dataBean.getCover().toString());
        }else {
            Glide.with(mContext).load(dataBean.getCover().getFeed()).into((ImageView) helper.getView(R.id.item_video_page_img));

        }
        if (!TextUtils.isEmpty(dataBean.getTitle())){
            helper.setText(R.id.item_video_title_tv, dataBean.getTitle());

        }
        if (!TextUtils.isEmpty(dataBean.getCategory())){
            helper.setText(R.id.item_video_type_tv, dataBean.getCategory());
        }

    }
}
