package com.gjf.lovezzu.entity;

import java.util.List;

/**
 * Created by zhao on 2017/8/2.
 */

public class ShoppingCartDateBridging {
    private String count;
    private TaoyuGoodsResult goods;

    public TaoyuGoodsResult getGoods() {
        return goods;
    }

    public void setGoods(TaoyuGoodsResult goods) {
        this.goods = goods;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
