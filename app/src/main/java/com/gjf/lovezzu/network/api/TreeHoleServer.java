package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.treehole.TreeHoleData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/17.
 */

public interface TreeHoleServer {
    @FormUrlEncoded
    @POST("TreeHoleAction")
    Observable<TreeHoleData> getTreeHole(@Field("action")String action,@Field("SessionID")String sessionID);
}
