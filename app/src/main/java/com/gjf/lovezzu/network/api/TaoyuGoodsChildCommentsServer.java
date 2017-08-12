package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.taoyu.GoodsChildCommentsData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/3.
 */

public interface TaoyuGoodsChildCommentsServer {
    @FormUrlEncoded
    @POST("comments_L2Action")
    Observable<GoodsChildCommentsData> getChileComments(@Field("action") String action,@Field("L1_Cid")String L1_Cid);

}
