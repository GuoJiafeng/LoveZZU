package com.gjf.lovezzu.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.network.api.DownloadIconServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by BlackBeard丶 on 2017/03/09.
 */
public class DownloadIconMethods {
    private Retrofit retrofit;
    private DownloadIconServer downloadIconServer;
    private static final int DEFAULT_TIMEOUT = 5;

    private DownloadIconMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().
                baseUrl(Url.LOGIN_URL).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
        downloadIconServer = retrofit.create(DownloadIconServer.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final DownloadIconMethods INSTANCE = new DownloadIconMethods();
    }

    //获取单例
    public static DownloadIconMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void startDownloadIcon(Subscriber<Bitmap> subscriber,String imageURL,String action) {
        downloadIconServer.downloadIconFromNet(imageURL, action).
                subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {

                        Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
