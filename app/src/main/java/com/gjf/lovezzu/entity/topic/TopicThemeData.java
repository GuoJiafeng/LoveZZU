package com.gjf.lovezzu.entity.topic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/12.
 */

public class TopicThemeData implements Serializable {
    private String result;
    private List<TopicThemeBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TopicThemeBridging> getValues() {
        return values;
    }

    public void setValues(List<TopicThemeBridging> values) {
        this.values = values;
    }
}
