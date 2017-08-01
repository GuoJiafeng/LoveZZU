package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.GoodsCommentsData;



import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/7/31.
 */

public interface TaoyuGoodsCommentsServer {
    @FormUrlEncoded
    @POST("comments_L1Action")
    Observable<GoodsCommentsData> getTaoyuGoodsComments(@Field("action") String action,@Field("goods_id") String goods_id);



}
