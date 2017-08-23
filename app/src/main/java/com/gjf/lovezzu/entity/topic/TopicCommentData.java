package com.gjf.lovezzu.entity.topic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/15.
 */

public class TopicCommentData implements Serializable{
    private String result;
    private List<TopicCommBridging> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TopicCommBridging> getValues() {
        return values;
    }

    public void setValues(List<TopicCommBridging> values) {
        this.values = values;
    }
}
