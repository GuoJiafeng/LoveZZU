package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.parttimejob.JobData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/18.
 */

public interface JobServer {
    @FormUrlEncoded
    @POST("PartTimeAction")
    Observable<JobData> getAllJob(@Field("action")String action);

    @FormUrlEncoded
    @POST("PartTimeAction")
    Observable<JobData> getMyJob(@Field("action")String action,@Field("SessionID")String sessionID);

    @FormUrlEncoded
    @POST("PartTimeAction")
    Observable<JobData> getSearchJob(@Field("action")String action,@Field("title")String title);

}
