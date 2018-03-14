package com.smile.smilevideo.home;

import com.smile.smilevideo.entity.HomeEntity;

import java.util.List;

/**
 * Created by yaojiulong on 2017/9/11.
 */

public interface IHomeView {
    void setItemListData(List<HomeEntity.ItemListBean> listData);

}
