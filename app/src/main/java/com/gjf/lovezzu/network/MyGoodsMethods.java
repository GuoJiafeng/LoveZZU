package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.MyPublishGoodsData;
import com.gjf.lovezzu.network.api.MyGoodsServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/7.
 */

public class MyGoodsMethods {
    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT=5;
    private MyGoodsServer myGoodsServer;
    private MyGoodsMethods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .baseUrl(Url.LOGIN_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myGoodsServer=retrofit.create(MyGoodsServer.class);
    }

    private static class SinglerHolder{
        private static final MyGoodsMethods INSTANCE=new MyGoodsMethods();

    }

    public static MyGoodsMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }


    public void getMyGoods(Subscriber<MyPublishGoodsData> subscriber,String sessionID,String action){
        myGoodsServer.getMyGoods(sessionID,action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
