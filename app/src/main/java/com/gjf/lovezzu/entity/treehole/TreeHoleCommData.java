package com.gjf.lovezzu.entity.treehole;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/17.
 */

public class TreeHoleCommData implements Serializable{
    private String result;
    private List<TreeHoleComm> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TreeHoleComm> getValues() {
        return values;
    }

    public void setValues(List<TreeHoleComm> values) {
        this.values = values;
    }
}
