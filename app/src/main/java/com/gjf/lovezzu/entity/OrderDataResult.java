package com.gjf.lovezzu.entity;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderDataResult {
    private Goods goods;
    private UserInfoResult userinfo;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
