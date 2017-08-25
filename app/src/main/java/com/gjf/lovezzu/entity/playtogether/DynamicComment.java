package com.gjf.lovezzu.entity.playtogether;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by lenovo047 on 2017/8/22.
 */

public class DynamicComment implements Serializable {
    private UserInfoResult userinfo;
    private CommentDynamic dynamicComment;

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }

    public CommentDynamic getDynamicComment() {
        return dynamicComment;
    }

    public void setDynamicComment(CommentDynamic dynamicComment) {
        this.dynamicComment = dynamicComment;
    }
}
