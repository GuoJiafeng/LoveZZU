package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.topic.TopicData;
import com.gjf.lovezzu.network.api.TopicServer;

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

public class TopicMethods {

    private Retrofit retrofit;
    private TopicServer topicServer;
    private static final int DEFAULT_TIMEOUT=5;

    private TopicMethods(){
        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();

        topicServer=retrofit.create(TopicServer.class);
    }

    private static class SinglerHolder{
        private final static TopicMethods INSTANCE=new TopicMethods();
    }

    public static TopicMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getTopic(Subscriber<TopicData> subscriber,String themeId,String action,String SeesionID){
        topicServer.getTopic(themeId,action,SeesionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
