package com.smile.smilevideo.home;

import android.content.Context;
import android.util.Log;

import com.smile.smilevideo.base.BasePresenter;
import com.smile.smilevideo.entity.HomeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaojiulong on 2017/9/11.
 */

public class HomePresenter extends BasePresenter<IHomeView> {
    private Context mContext;
    private IHomeView mView;

    public HomePresenter(Context mContext, IHomeView view) {
        this.mContext = mContext;
        this.mView = view;
        attachView(mView);
    }

    /**
     * 获取首页信息，对获取的数据进行处理，最后返回给UI层
     * @param num
     */
    public void getSomeInfo(String num){
        Disposable disposable = returnApiStore().getHomeInfo(getParams(num))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<HomeEntity, List<HomeEntity.ItemListBean>>() {
                    @Override
                    public List<HomeEntity.ItemListBean> apply(HomeEntity homeEntity) throws Exception {
                        List<HomeEntity.ItemListBean> oldList = homeEntity.getItemList();
                        List<HomeEntity.ItemListBean> newList = new ArrayList<HomeEntity.ItemListBean>();
                        for (HomeEntity.ItemListBean listBean : oldList) {
                            if (listBean.getType().equals("video")){
                                newList.add(listBean);
                            }
                        }
                        return newList;
                    }
                })
                .subscribe(new Consumer<List<HomeEntity.ItemListBean>>() {
                    @Override
                    public void accept(List<HomeEntity.ItemListBean> homeEntity) throws Exception {
                        Log.e("dandy", "Success  " +homeEntity.toString());
                        mView.setItemListData(homeEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("dandy", "error "+ throwable.toString());
                    }
                });
        addDisposable(disposable);
    }

    private Map<String, String> getParams(String num){
        Map<String, String> map = new HashMap<>();
        map.put("num", num);
        map.put("page", num);
        return map;
    }

}
