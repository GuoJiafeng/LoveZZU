package com.gjf.lovezzu.entity.school;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/11.
 */

public class TopNewsData implements Serializable {
    private String result;
    private List<TopNewsResult> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TopNewsResult> getValues() {
        return values;
    }

    public void setValues(List<TopNewsResult> values) {
        this.values = values;
    }
}
