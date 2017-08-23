package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.treehole.TreeHoleCommData;
import com.gjf.lovezzu.network.api.TreeHoleCommServer;

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

public class TreeHoleCommMethods {
    private Retrofit retrofit;
    private TreeHoleCommServer treeHoleCommServer;
    private static final int DEFAULT_TIMEOUT = 5;
    private TreeHoleCommMethods (){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        treeHoleCommServer=retrofit.create(TreeHoleCommServer.class);
    }
    private static class SinglerHolder{
        private static final TreeHoleCommMethods INSTANCE=new TreeHoleCommMethods();
    }
    public static TreeHoleCommMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getTreeHoleComm(Subscriber<TreeHoleCommData> subscriber,String action,String treeHoleID){
        treeHoleCommServer.getTreeHoleComm(action,treeHoleID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
