package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.taoyu.TaoyuGoodsData;
import com.gjf.lovezzu.network.api.TaoyuGoodsListServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by BlackBeard丶 on 2017/04/09.
 */

public class TaoyuGoodsListMethods {
    private Retrofit retrofit;
    private TaoyuGoodsListServer taoyuGoodsListServer;
    private static final int DEFAULT_TIMEOUT = 5;
    private TaoyuGoodsListMethods(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();

        taoyuGoodsListServer = retrofit.create(TaoyuGoodsListServer.class);
    }
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final TaoyuGoodsListMethods INSTANCE = new TaoyuGoodsListMethods() {
        };
    }

    //获取单例
    public static TaoyuGoodsListMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getGoodsList(Subscriber<TaoyuGoodsData> subscriber, String action, int num){
        taoyuGoodsListServer.getTaoyuGoodsList(action,num).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
