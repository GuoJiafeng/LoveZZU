package com.gjf.lovezzu.entity.taoyu;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderDataResult implements Serializable{
    private TaoyuGoodsResult goods;
    private UserInfoResult userinfo;
    private int count;
    private int total;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
