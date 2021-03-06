package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.saylove.SayloveData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Leon on 2017/8/18.
 */

public interface SearchlovecardServer {
    @FormUrlEncoded
    @POST("LoveCardAction")
    Observable<SayloveData> getSearchlovecard(@Field("action") String action, @Field("search") String search);
}
