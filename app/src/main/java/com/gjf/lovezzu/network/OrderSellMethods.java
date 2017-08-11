package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.OrderSellData;
import com.gjf.lovezzu.network.api.OrderSellServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderSellMethods {


    private Retrofit retrofit;
    private OrderSellServer orderSellServer;
    private static final int DEFAULT_TIMEOUT = 5;

    private OrderSellMethods(){
        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();

        orderSellServer=retrofit.create(OrderSellServer.class);
    }

    private static class SinglerHolder{
        private static final OrderSellMethods INSTANCE=new OrderSellMethods();
    }

    public static OrderSellMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getOrderSell(Subscriber<OrderSellData> subscriber,String action,String sessionID){
        orderSellServer.getSellOrder(action,sessionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
