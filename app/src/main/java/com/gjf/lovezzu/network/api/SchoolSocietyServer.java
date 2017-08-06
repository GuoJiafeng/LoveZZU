package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.SocietyNewsData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/6.
 */

public interface SchoolSocietyServer {
    @FormUrlEncoded
    @POST("index")
    Observable<SocietyNewsData> getSocietyNews(@Field("key")String key,@Field("type")String type);
}
