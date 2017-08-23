package com.gjf.lovezzu.network.api;



import com.gjf.lovezzu.entity.saylove.SayloveReplyData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Leon on 2017/8/18.
 */

public interface SayloveReplyServer {
    @FormUrlEncoded
    @POST("LoveCardCommentAction")
    Observable<SayloveReplyData> getSayloveReply(@Field("action") String action, @Field("loveCardId") String loveCardId);
}

