package com.gjf.lovezzu.entity;

import java.util.List;

/**
 * Created by zhao on 2017/8/2.
 */

public class ShoppingCartDateBridging {
    private String count;
    private TaoyuGoodsResult goodsResult;
    public TaoyuGoodsResult getGoodsResult() {
        return goodsResult;
    }

    public void setGoodsResult(TaoyuGoodsResult goodsResult) {
        this.goodsResult = goodsResult;
    }



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
