package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.taoyu.ShoppingCartDate;



import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/2.
 */

public interface ShoppingCartServer {
    @FormUrlEncoded
    @POST("cartAction")
    Observable<ShoppingCartDate> getShoppingCart(@Field("SessionID" )String SessionID, @Field("action") String action);
}
