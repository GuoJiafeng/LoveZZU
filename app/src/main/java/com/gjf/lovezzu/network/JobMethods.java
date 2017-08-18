package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.parttimejob.JobData;
import com.gjf.lovezzu.network.api.JobServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/18.
 */

public class JobMethods {
    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT=5;
    private JobServer jobServer;
    private JobMethods(){
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(Url.LOGIN_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jobServer=retrofit.create(JobServer.class);
    }

    private static class SinglerHolder{
        private static final JobMethods INSTANCE=new JobMethods();
    }

    public static JobMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void ggetAllJob(Subscriber<JobData> subscriber,String action){
        jobServer.getAllJob(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMyJob(Subscriber<JobData> subscriber,String action,String sessionId){
        jobServer.getMyJob(action,sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getSearchJob(Subscriber<JobData> subscriber,String action,String title){
        jobServer.getSearchJob(action,title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
