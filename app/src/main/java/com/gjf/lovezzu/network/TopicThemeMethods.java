package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.topic.TopicThemeData;
import com.gjf.lovezzu.network.api.TopicThemeServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/12.
 */

public class TopicThemeMethods {

    private Retrofit retrofit;
    private TopicThemeServer topicThemeServer;
    private static final int DEFAULT_TIMEOUT=5;
    private TopicThemeMethods(){
        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        topicThemeServer=retrofit.create(TopicThemeServer.class);

    }

    private static class SinglerHolder{
        private static final TopicThemeMethods INSTANCE=new TopicThemeMethods();
    }


    public  static  TopicThemeMethods getInstance(){
        return  SinglerHolder.INSTANCE;
    }


    public  void getTheme(Subscriber<TopicThemeData> subscriber,String action){
        topicThemeServer.getTheme(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
