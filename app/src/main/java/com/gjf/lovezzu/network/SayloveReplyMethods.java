package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.saylove.SayloveReplyData;
import com.gjf.lovezzu.network.api.SayloveReplyServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leon on 2017/8/18.
 */

public class SayloveReplyMethods {
    private Retrofit retrofit;
    private SayloveReplyServer sayloveReplyServer;
    private static final int DEFAULT_TIMEOUT = 5;

    public SayloveReplyMethods() {
        OkHttpClient.Builder httpClientBuilder =new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        sayloveReplyServer=retrofit.create(SayloveReplyServer.class);
    }
    private  static class  SinglerHolder{
        private  static  final SayloveReplyMethods INSTANCE = new SayloveReplyMethods();
    }
    public  static  SayloveReplyMethods getInstance(){
        return  SinglerHolder.INSTANCE;
    }
    public  void getSayloveReply(Subscriber<SayloveReplyData> subscriber, String action, String loveCardId){
        sayloveReplyServer.getSayloveReply(action, loveCardId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
