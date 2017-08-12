package com.gjf.lovezzu.entity.taoyu;

import java.util.List;

/**
 * Created by BlackBeard丶 on 2017/04/13.
 */

public class TaoyuGoodsData {

    private String result;
    private List<TaoyuDataBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TaoyuDataBridging> getValues() {
        return values;
    }

    public void setValues(List<TaoyuDataBridging> values) {
        this.values = values;
    }
}
