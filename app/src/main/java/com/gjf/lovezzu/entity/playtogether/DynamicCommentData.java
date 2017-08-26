package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo047 on 2017/8/22.
 */

public class DynamicCommentData implements Serializable{
    private String result;
    private List<DynamicComment>  values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<DynamicComment> getValues() {
        return values;
    }

    public void setValues(List<DynamicComment> values) {
        this.values = values;
    }
}
