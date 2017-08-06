package com.gjf.lovezzu.entity;

import java.util.List;

/**
 * Created by zhao on 2017/8/6.
 */

public class SocietyNewsResultBridging {
    private String stat;
    private List<SocietyNewsResult> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<SocietyNewsResult> getData() {
        return data;
    }

    public void setData(List<SocietyNewsResult> data) {
        this.data = data;
    }
}
