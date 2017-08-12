package com.gjf.lovezzu.entity.taoyu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderDataBridging implements Serializable{
    private Order orderdata;
    private List<OrderDataResult> ugoods;

    public Order getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(Order orderdata) {
        this.orderdata = orderdata;
    }

    public List<OrderDataResult> getUgoods() {
        return ugoods;
    }

    public void setUgoods(List<OrderDataResult> ugoods) {
        this.ugoods = ugoods;
    }
}
