package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.GoodsChildCommentsData;
import com.gjf.lovezzu.network.api.TaoyuGoodsChildCommentsServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/3.
 */

public class TaoyuGoodsChildCommetnsMethods {

    private Retrofit retrofit;
    private TaoyuGoodsChildCommentsServer taoyuGoodsChildCommentsServer;
    private static final int DEFAULT_TIMEOUT=5;

    private TaoyuGoodsChildCommetnsMethods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(Url.LOGIN_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taoyuGoodsChildCommentsServer=retrofit.create(TaoyuGoodsChildCommentsServer.class);
    };

    private static class SinglerHolder{
        private static final TaoyuGoodsChildCommetnsMethods INSTANCE=new TaoyuGoodsChildCommetnsMethods();
    }

    public static TaoyuGoodsChildCommetnsMethods getInsance(){
        return SinglerHolder.INSTANCE;
    }


    public void getChileComments(Subscriber<GoodsChildCommentsData> subscriber,String action ,String L1_Cid){
        taoyuGoodsChildCommentsServer.getChileComments(action,L1_Cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
