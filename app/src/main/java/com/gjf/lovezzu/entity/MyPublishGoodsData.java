package com.gjf.lovezzu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/7.
 */

public class MyPublishGoodsData implements Serializable{
    private String result;
    private List<TaoyuGoodsResult> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TaoyuGoodsResult> getValues() {
        return values;
    }

    public void setValues(List<TaoyuGoodsResult> values) {
        this.values = values;
    }
}
