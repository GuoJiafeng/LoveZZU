package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.topic.TopicCommentData;
import com.gjf.lovezzu.network.api.TopicCommServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/15.
 */

public class TopicCommMethods {

    private Retrofit retrofit;
    private TopicCommServer topicCommServer;
    private static final int DEFAULT_TIMEOUT=5;

    private TopicCommMethods(){
        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        topicCommServer=retrofit.create(TopicCommServer.class);
    }

    private static class SinglerHolder{
        private final static TopicCommMethods INSTANCE=new TopicCommMethods();
    }

    public static TopicCommMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getComm(Subscriber<TopicCommentData> subscriber,String action,String topicId){
        topicCommServer.getTopicComm(action,topicId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
