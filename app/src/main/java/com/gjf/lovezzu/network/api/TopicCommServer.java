package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.topic.TopicCommentData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/15.
 */

public interface TopicCommServer {
    @FormUrlEncoded
    @POST("TopicCommentAction")
    Observable<TopicCommentData> getTopicComm(@Field("action")String action,@Field("TopicId")String topicId);
}
