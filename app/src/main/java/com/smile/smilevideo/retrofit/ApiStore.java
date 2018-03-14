package com.smile.smilevideo.retrofit;

import com.smile.smilevideo.entity.HomeEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by yaojiulong on 2017/8/21.
 */

public interface ApiStore {
     String BASE_URL="http://baobab.kaiyanapp.com/api/";

     @GET("v4/tabs/selected")
     Observable<HomeEntity> getHomeInfo(@QueryMap Map<String,String> map);
}
