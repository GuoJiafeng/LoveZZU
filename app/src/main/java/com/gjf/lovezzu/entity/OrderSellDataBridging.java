package com.gjf.lovezzu.entity;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderSellDataBridging implements Serializable{
    private Order order;
    private TaoyuGoodsResult goods;
    private int count;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TaoyuGoodsResult getGoods() {
        return goods;
    }

    public void setGoods(TaoyuGoodsResult goods) {
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
