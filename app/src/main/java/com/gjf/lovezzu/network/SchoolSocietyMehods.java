package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.SocietyNewsData;
import com.gjf.lovezzu.network.api.SchoolSocietyServer;

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

public class SchoolSocietyMehods {

    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT=5;
    private SchoolSocietyServer schoolSocietyServer;
    private SchoolSocietyMehods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.SOCIETY_API)
                .build();
        schoolSocietyServer=retrofit.create(SchoolSocietyServer.class);
    }

    private static class SinglerHolder{
        private static final SchoolSocietyMehods INSTANCE=new SchoolSocietyMehods();
    }

    public static SchoolSocietyMehods getInstance(){
        return  SinglerHolder.INSTANCE;
    }

    public void getSocietyNews(Subscriber<SocietyNewsData> subscriber,String key,String type){
        schoolSocietyServer.getSocietyNews(key,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
