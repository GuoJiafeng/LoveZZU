package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.saylove.SayloveData;
import com.gjf.lovezzu.network.api.SearchlovecardServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leon on 2017/8/18.
 */

public class SearchlovecardMethods {
    private Retrofit  retrofit;
    private SearchlovecardServer searchlovecardServer;
    private  static final int DEFAULT_TIMEOUT = 5;

    public SearchlovecardMethods() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        searchlovecardServer=retrofit.create(SearchlovecardServer.class);
    }
   private  static class  SingletonHolder{
        private  static final SearchlovecardMethods INSTANCE=new SearchlovecardMethods(){

        };
    }
    public  static SearchlovecardMethods getInstance(){
        return  SingletonHolder.INSTANCE;
    }
    public  void getSearchlovecard(Subscriber<SayloveData> subscriber,String action,String search){
        searchlovecardServer.getSearchlovecard(action, search).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    }
