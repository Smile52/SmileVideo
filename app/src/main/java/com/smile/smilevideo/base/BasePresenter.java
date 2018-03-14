package com.smile.smilevideo.base;

import com.smile.smilevideo.retrofit.ApiStore;
import com.smile.smilevideo.retrofit.RetrofitClient;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaojiulong on 2017/8/21.
 */

public class BasePresenter<V> {
    public V mView;
    protected ApiStore mApiStore;
    protected CompositeDisposable mCompositeDisposable;

    public void attachView(V view){
        this.mView = view;
        mApiStore = RetrofitClient.retrofit().create(ApiStore.class);
    }



    protected void addDisposable(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unDisposable(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    public void closeRetrofit(){
        RetrofitClient.close();
    }

    public void detachView(){
        this.mView = null;
        unDisposable();
        closeRetrofit();
    }

    public ApiStore returnApiStore(){
        return mApiStore;
    }
}
