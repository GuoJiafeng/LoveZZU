package com.gjf.lovezzu.network.api;

import com.gjf.lovezzu.entity.CommResult;
import com.gjf.lovezzu.entity.playtogether.DynamicCommentData;
import com.gjf.lovezzu.entity.playtogether.GroupData;
import com.gjf.lovezzu.entity.playtogether.GroupDynamicData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhao on 2017/8/20.
 */

public interface GroupServer {
    //分页查询群组
    @FormUrlEncoded
    @POST("GroupAction")
    Observable<GroupData> getGroup(@Field("action")String action,@Field("num")int num);

    //搜索群组
    @FormUrlEncoded
    @POST("GroupAction")
    Observable<GroupData> getSearchGroup(@Field("search")String search,@Field("action")String action);

    //我发布的群组或者我加入的群组
    @FormUrlEncoded
    @POST("GroupAction")
    Observable<GroupData> getMyGroup(@Field("SessionID")String SessionID,@Field("action")String action);

    //加入群组
    @FormUrlEncoded
    @POST("GroupAction")
    Observable<CommResult> joinGroup(@Field("SessionID")String SessionID, @Field("action")String action, @Field("groupId")String groupId);


    //是否加入群组
    @FormUrlEncoded
    @POST("GroupAction")
    Observable<CommResult> isJoinGroup(@Field("SessionID")String SessionID, @Field("action")String action, @Field("groupId")String groupId);

    //退出群组
    @FormUrlEncoded
    @POST("GroupAction")
    Observable<CommResult> quitGroup(@Field("SessionID")String SessionID, @Field("action")String action, @Field("groupId")String groupId);

    //删除照片
    @FormUrlEncoded
    @POST("imagesupload")
    Observable<CommResult> deleteImage(@Field("ImagesName")String ImagesName, @Field("action")String action, @Field("groupId")String groupId);

    //查询群动态
    @FormUrlEncoded
    @POST("GroupDynamicAction")
    Observable<GroupDynamicData> getDynamic(@Field("SessionID")String SessionID,@Field("groupId")String groupId,@Field("action")String action,@Field("num") int num);

    //查询群动态评论
    @FormUrlEncoded
    @POST("DynamicCommentAction")
    Observable<DynamicCommentData> getDynamicComment(@Field("dynamicId") String dynamicId, @Field("action") String action);



}
