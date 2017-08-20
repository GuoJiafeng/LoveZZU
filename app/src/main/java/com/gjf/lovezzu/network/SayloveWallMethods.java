package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.saylove.SayloveData;
import com.gjf.lovezzu.network.api.SayloveServer;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gjf.lovezzu.constant.Url.LOGIN_URL;

/**
 * Created by Leon on 2017/8/18.
 */

public class SayloveWallMethods {
    private Retrofit retrofit;
    private SayloveServer sayloveServer;
    private static final int DEFAULT_TIMEOUT = 5;

    public SayloveWallMethods() {
        OkHttpClient.Builder httpClientBuilder =new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        sayloveServer=retrofit.create(SayloveServer.class);
    }
    private static class SinglerHolder{
        private  static final SayloveWallMethods INSTANCE=new SayloveWallMethods();
    }
    public  static SayloveWallMethods getInstance(){
        return  SinglerHolder.INSTANCE;
    }
    public  void getSaylove(Subscriber<SayloveData> subscriber, String action){
        sayloveServer.getSaylove(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
