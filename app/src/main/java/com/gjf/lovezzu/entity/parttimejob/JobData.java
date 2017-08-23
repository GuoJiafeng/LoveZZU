package com.gjf.lovezzu.entity.parttimejob;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/18.
 */

public class JobData implements Serializable {
    private String result;
    private List<JobResult> values;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<JobResult> getValues() {
        return values;
    }

    public void setValues(List<JobResult> values) {
        this.values = values;
    }
}
