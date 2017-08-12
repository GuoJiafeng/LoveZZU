package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.taoyu.OrderSellData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/6.
 */

public interface OrderSellServer {
    @FormUrlEncoded
    @POST("OrderAction")
    Observable<OrderSellData> getSellOrder(@Field("action")String action,@Field("SessionID")String sessionID);
}
