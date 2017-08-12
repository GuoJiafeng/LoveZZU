package com.gjf.lovezzu.entity.taoyu;

import java.util.List;

/**
 * Created by zhao on 2017/8/2.
 */

public class ShoppingCartDate {
    private String result;
    private List<ShoppingCartDateBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ShoppingCartDateBridging> getValues() {
        return values;
    }

    public void setValues(List<ShoppingCartDateBridging> values) {
        this.values = values;
    }
}
