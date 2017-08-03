package com.gjf.lovezzu.entity;

import java.util.List;

/**
 * Created by zhao on 2017/8/3.
 */

public class GoodsChildCommentsData {
    private String result;
    private List<GoodsChildCommentsDateBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<GoodsChildCommentsDateBridging> getValues() {
        return values;
    }

    public void setValues(List<GoodsChildCommentsDateBridging> values) {
        this.values = values;
    }
}
