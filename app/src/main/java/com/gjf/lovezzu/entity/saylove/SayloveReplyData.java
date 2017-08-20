package com.gjf.lovezzu.entity.saylove;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leon on 2017/8/18.
 */

public class SayloveReplyData implements Serializable {
  private   String result;
    private  List<SayloveReply>  values;

    public List<SayloveReply> getValues() {
        return values;
    }

    public void setValues(List<SayloveReply> values) {
        this.values = values;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
