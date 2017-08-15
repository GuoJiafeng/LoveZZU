package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.topic.TopicThemeData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/12.
 */

public interface TopicThemeServer {
    @FormUrlEncoded
    @POST("ThemeAction")
    Observable<TopicThemeData> getTheme(@Field("action")String action);
}
