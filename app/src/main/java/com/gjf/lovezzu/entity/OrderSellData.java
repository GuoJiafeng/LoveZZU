package com.gjf.lovezzu.entity;

import java.util.List;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderSellData {
    private List<OrderSellDataBridging> OrderGoods;

    public List<OrderSellDataBridging> getOrderGoods() {
        return OrderGoods;
    }

    public void setOrderGoods(List<OrderSellDataBridging> orderGoods) {
        OrderGoods = orderGoods;
    }
}
