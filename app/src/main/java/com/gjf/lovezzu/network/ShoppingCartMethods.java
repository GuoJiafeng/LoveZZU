package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.ShoppingCartDate;
import com.gjf.lovezzu.network.api.ShoppingCartServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/2.
 */

public class ShoppingCartMethods {
    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT=5;
    private ShoppingCartServer shoppingCartServer;
    private ShoppingCartMethods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        shoppingCartServer=retrofit.create(ShoppingCartServer.class);
    }

    private static class SinglerHolder{
        private static final ShoppingCartMethods INSTANCE=new ShoppingCartMethods();
    }

    public static ShoppingCartMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getShoppingCart(Subscriber<ShoppingCartDate> subscriber,String SessionID,String action){
        shoppingCartServer.getShoppingCart(SessionID,action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
