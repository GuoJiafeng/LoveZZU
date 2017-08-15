package com.gjf.lovezzu.entity.topic;

import java.util.List;

/**
 * Created by zhao on 2017/8/12.
 */

public class TopicData {
    private String result;
    private List<TopicDataBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TopicDataBridging> getValues() {
        return values;
    }

    public void setValues(List<TopicDataBridging> values) {
        this.values = values;
    }
}
