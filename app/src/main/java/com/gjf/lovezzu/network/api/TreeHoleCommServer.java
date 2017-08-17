package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.treehole.TreeHoleCommData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/17.
 */

public interface TreeHoleCommServer {
    @FormUrlEncoded
    @POST("TreeHoleCommentAction")
    Observable<TreeHoleCommData> getTreeHoleComm(@Field("action")String action,@Field("treeHoleId")String treeholeID);
}
