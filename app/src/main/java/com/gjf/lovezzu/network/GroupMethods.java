package com.gjf.lovezzu.network;

import com.gjf.lovezzu.constant.Url;
import com.gjf.lovezzu.entity.CommResult;
import com.gjf.lovezzu.entity.playtogether.DynamicCommentData;
import com.gjf.lovezzu.entity.playtogether.GroupData;
import com.gjf.lovezzu.entity.playtogether.GroupDynamicData;
import com.gjf.lovezzu.network.api.GroupServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhao on 2017/8/20.
 */

public class GroupMethods {

    private Retrofit retrofit;
    private GroupServer groupServer;
    private static final int DEFAULT_TIMEOUT = 5;
    private GroupMethods(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.LOGIN_URL)
                .build();
        groupServer=retrofit.create(GroupServer.class);
    }

    private static class SinglerHolder{
      private final static GroupMethods INSTANCE=new GroupMethods();
    }

    public static GroupMethods getInstance(){
        return SinglerHolder.INSTANCE;
    }

    public void getGroup(Subscriber<GroupData> subscriber,String action,int num){
        groupServer.getGroup(action,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void getSearchGroup(Subscriber<GroupData> subscriber,String search,String action){
        groupServer.getSearchGroup(search,action) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void joinGroup(Subscriber<CommResult> subscriber,String SessionID,String action,String groupId){
        groupServer.joinGroup(SessionID,action,groupId) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void isJoinGroup(Subscriber<CommResult> subscriber,String SessionID,String action,String groupId ){
        groupServer.isJoinGroup(SessionID,action,groupId) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void quitGroup(Subscriber<CommResult> subscriber,String SessionID,String action,String groupId){
        groupServer.quitGroup(SessionID,action,groupId) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void deleteImage(Subscriber<CommResult> subscriber,String ImagesName,String action,String groupId){
        groupServer.deleteImage(ImagesName,action,groupId) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public void getDynamic(Subscriber<GroupDynamicData> subscriber,String SessionID,String groupId,String action,int num){
        groupServer.getDynamic(SessionID,groupId, action, num) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public  void getDynamicComment(Subscriber<DynamicCommentData> subscriber, String dynamicId, String action){
        groupServer.getDynamicComment(dynamicId,action).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMyGroup(Subscriber<GroupData> subscriber,String action,String SessionID){
        groupServer.getMyGroup(SessionID,action).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
