package com.gjf.lovezzu.entity.taoyu;


import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhao on 2017/7/31.
 */

public class GoodsCommentsDataBridging implements Serializable{
    private GoodsCommentsResult comments_L1;
    private UserInfoResult userinfo;

    public GoodsCommentsResult getComments_L1() {
        return comments_L1;
    }

    public void setComments_L1(GoodsCommentsResult comments_L1) {
        this.comments_L1 = comments_L1;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
