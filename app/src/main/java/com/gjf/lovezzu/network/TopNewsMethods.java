package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.school.TopNewsData;
import com.gjf.lovezzu.network.api.TopNewsServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/11.
 */

public class TopNewsMethods {

    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 5;
    private TopNewsServer topNewsServer;
    private TopNewsMethods(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
       retrofit=new Retrofit.Builder()
               .client(httpClientBuilder.build())
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .baseUrl(Url.LOGIN_URL)
               .build();
        topNewsServer=retrofit.create(TopNewsServer.class);
    }

    private static class SinglerHolder{
        private final static TopNewsMethods INSTANCE=new TopNewsMethods();
    }

    public static TopNewsMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }


    public void getTopNews(Subscriber<TopNewsData> subscriber,String action){
        topNewsServer.getTopNews(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
