package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.taoyu.TaoyuGoodsData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by BlackBeard丶 on 2017/04/09.
 */

public interface TaoyuGoodsListServer {

    @FormUrlEncoded
    @POST("querygoodsAction")
    Observable<TaoyuGoodsData> getTaoyuGoodsList(@Field("action") String action, @Field("num") int num);


}
