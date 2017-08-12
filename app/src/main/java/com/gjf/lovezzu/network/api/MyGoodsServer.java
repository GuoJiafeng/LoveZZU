package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.taoyu.MyPublishGoodsData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/7.
 */

public interface MyGoodsServer {
    @FormUrlEncoded
    @POST("querygoodsAction")
    Observable<MyPublishGoodsData> getMyGoods(@Field("SessionID")String sessionID,@Field("action")String action);
}
