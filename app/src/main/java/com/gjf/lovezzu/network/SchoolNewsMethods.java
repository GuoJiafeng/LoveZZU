package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.school.SchoolNewsData;
import com.gjf.lovezzu.network.api.SchoolNewServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by BlackBeard丶 on 2017/03/23.
 */
public class SchoolNewsMethods {
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private SchoolNewServer schoolNewServer;

    private SchoolNewsMethods() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Url.News_Url)
                .build();

        schoolNewServer = retrofit.create(SchoolNewServer.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final SchoolNewsMethods INSTANCE = new SchoolNewsMethods();
    }

    //获取单例
    public static SchoolNewsMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public void getSchoolNews(Subscriber<SchoolNewsData> subscriber, int page) {
        schoolNewServer.getNews(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
