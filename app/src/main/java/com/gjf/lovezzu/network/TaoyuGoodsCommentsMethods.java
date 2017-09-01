package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.taoyu.GoodsCommentsData;
import com.gjf.lovezzu.network.api.TaoyuGoodsCommentsServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/7/31.
 */

public class TaoyuGoodsCommentsMethods {
    private Retrofit retrofit;
    private TaoyuGoodsCommentsServer taoyuGoodsCommentsServer;
    private static final int DEFAULT_TIMEOUT=5;
    private TaoyuGoodsCommentsMethods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        taoyuGoodsCommentsServer=retrofit.create(TaoyuGoodsCommentsServer.class);
    }

    private static class SinglerHolder{
        private static final TaoyuGoodsCommentsMethods INSTANCE=new TaoyuGoodsCommentsMethods(){};
    }

    public static TaoyuGoodsCommentsMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getTaoyuGoodsComments(Subscriber<GoodsCommentsData> subscriber, String action, String goods_id,String SessionID){
        taoyuGoodsCommentsServer.getTaoyuGoodsComments(action,goods_id,SessionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
