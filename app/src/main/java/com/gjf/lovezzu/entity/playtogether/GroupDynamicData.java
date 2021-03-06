package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/20.
 */

public class GroupDynamicData implements Serializable {
    private String result;
    private List<GroupDynamicResult> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<GroupDynamicResult> getValues() {
        return values;
    }

    public void setValues(List<GroupDynamicResult> values) {
        this.values = values;
    }
}
