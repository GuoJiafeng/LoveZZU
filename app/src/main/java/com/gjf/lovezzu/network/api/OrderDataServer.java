package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.taoyu.OrderData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/5.
 */

public interface OrderDataServer {
    @FormUrlEncoded
    @POST("OrderAction")
    Observable<OrderData> getOrderData(@Field("action")String action,@Field("SessionID")String sessionID);
}
