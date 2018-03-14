package com.smile.smilevideo.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by yaojiulong on 2016/12/22.
 */

public class RetrofitClient {
    public static Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 5;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

            /**
             * 添加拦截器，设置请求header
             */
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()

                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });


            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            Gson gson = new GsonBuilder()
                    //配置Gson
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();


            OkHttpClient okHttpClient = httpClientBuilder.build();

            mRetrofit=new Retrofit.Builder()
                    .baseUrl(ApiStore.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return mRetrofit;
    }

    public static void close(){
        mRetrofit=null;
    }

}
