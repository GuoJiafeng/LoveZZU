package com.gjf.lovezzu.entity;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderDataResult implements Serializable{
    private TaoyuGoodsResult goods;
    private UserInfoResult userinfo;

    public TaoyuGoodsResult getGoods() {
        return goods;
    }

    public void setGoods(TaoyuGoodsResult goods) {
        this.goods = goods;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
