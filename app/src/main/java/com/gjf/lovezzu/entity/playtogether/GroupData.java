package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/20.
 */

public class GroupData implements Serializable {
    private String result;
    private List<GroupResult> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<GroupResult> getValues() {
        return values;
    }

    public void setValues(List<GroupResult> values) {
        this.values = values;
    }
}
