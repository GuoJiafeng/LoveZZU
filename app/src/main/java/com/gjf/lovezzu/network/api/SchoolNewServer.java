package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.school.SchoolNewsData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by BlackBeard丶 on 2017/03/23.
 */
public interface SchoolNewServer {
    @GET("advisory.action")
    Observable<SchoolNewsData> getNews(@Query("page") int pageNO);
}
