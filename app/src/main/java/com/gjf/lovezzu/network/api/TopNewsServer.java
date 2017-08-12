package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.school.TopNewsData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/11.
 */

public interface TopNewsServer {
    @FormUrlEncoded
    @POST("RoastingAction")
    Observable<TopNewsData> getTopNews(@Field("action")String action);
}
