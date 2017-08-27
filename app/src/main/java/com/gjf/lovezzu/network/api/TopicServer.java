package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.topic.TopicData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/12.
 */

public interface TopicServer {
    @FormUrlEncoded
    @POST("TopicAction")
    Observable<TopicData> getTopic(@Field("ThemeId")String themeId,@Field("action")String action,@Field("SessionID")String SessionID);
}
