package com.gjf.lovezzu.entity.treehole;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/16.
 */

public class TreeHoleData implements Serializable {
    private String result;
    private List<TreeHole> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TreeHole> getValues() {
        return values;
    }

    public void setValues(List<TreeHole> values) {
        this.values = values;
    }
}
