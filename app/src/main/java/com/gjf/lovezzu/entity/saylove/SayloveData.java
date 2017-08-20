package com.gjf.lovezzu.entity.saylove;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leon on 2017/8/18.
 */

public class SayloveData implements  Serializable{
    private String result;
    private List<Saylove>   values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Saylove> getValues() {
        return values;
    }

    public void setValues(List<Saylove> values) {
        this.values = values;
    }
}
