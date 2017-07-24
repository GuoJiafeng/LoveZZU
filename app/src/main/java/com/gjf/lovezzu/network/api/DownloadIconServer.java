package com.gjf.lovezzu.network.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by BlackBeard丶 on 2017/03/09.
 */
public interface DownloadIconServer {
    @FormUrlEncoded
    @POST("filedownload")
    Observable<ResponseBody> downloadIconFromNet(@Field("imageURL") String imageURL, @Field("action") String action);
}
