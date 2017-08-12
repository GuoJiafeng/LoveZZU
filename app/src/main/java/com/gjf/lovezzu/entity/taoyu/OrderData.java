package com.gjf.lovezzu.entity.taoyu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderData implements Serializable{
    private String result;
    private List<OrderDataBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<OrderDataBridging> getValues() {
        return values;
    }

    public void setValues(List<OrderDataBridging> values) {
        this.values = values;
    }
}
