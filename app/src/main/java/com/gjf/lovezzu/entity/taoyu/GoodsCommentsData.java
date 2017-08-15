package com.gjf.lovezzu.entity.taoyu;

import java.util.List;

/**
 * Created by zhao on 2017/7/31.
 */

public class GoodsCommentsData {
    private String result;
    private List<GoodsCommentsDataBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<GoodsCommentsDataBridging> getValues() {
        return values;
    }

    public void setValues(List<GoodsCommentsDataBridging> values) {
        this.values = values;
    }
}
