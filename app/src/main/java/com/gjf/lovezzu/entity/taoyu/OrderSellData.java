package com.gjf.lovezzu.entity.taoyu;

import java.util.List;

/**
 * Created by zhao on 2017/8/6.
 */

public class OrderSellData {
    private String result;
    private List<OrderSellDataBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<OrderSellDataBridging> getValues() {
        return values;
    }

    public void setValues(List<OrderSellDataBridging> values) {
        this.values = values;
    }
}
