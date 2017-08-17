package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.treehole.TreeHoleData;
import com.gjf.lovezzu.network.api.TreeHoleServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/17.
 */

public class TreeHoleMethods {
    private Retrofit retrofit;
    private TreeHoleServer treeHoleServer;
    private static final int DEFAULT_TIMEOUT = 5;
    private TreeHoleMethods(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        treeHoleServer=retrofit.create(TreeHoleServer.class);
    }
    private static class SinglerHolder{
        private static  final TreeHoleMethods INSTANCE=new TreeHoleMethods();
    }
    public static TreeHoleMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getTreeHole(Subscriber<TreeHoleData> subscriber, String action, String sessionID){
        treeHoleServer.getTreeHole(action,sessionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
