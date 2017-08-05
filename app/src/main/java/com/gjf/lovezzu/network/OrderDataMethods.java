package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.OrderData;
import com.gjf.lovezzu.network.api.OrderDataServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderDataMethods {
    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT=5;
    private OrderDataServer orderDataServer;
    private OrderDataMethods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit=new Retrofit.Builder()
                .baseUrl(Url.LOGIN_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderDataServer=retrofit.create(OrderDataServer.class);
    }

    private static class SinglerHolder{
        private static OrderDataMethods INSTANCE=new OrderDataMethods();
    }

    public static OrderDataMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getOrderData(Subscriber<OrderData> subscriber,String action,String sessionID){
        orderDataServer.getOrderData(action,sessionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
