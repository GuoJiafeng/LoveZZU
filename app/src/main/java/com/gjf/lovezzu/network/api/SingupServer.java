package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.LoginResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by BlackBeard丶 on 2017/03/02.
 */
public interface SingupServer {
    @FormUrlEncoded
    @POST("Singup")
    Observable<LoginResult> register(@Field("issuccessful") boolean issuccessful,@Field("phone") String name,  @Field("password") String password);
}


