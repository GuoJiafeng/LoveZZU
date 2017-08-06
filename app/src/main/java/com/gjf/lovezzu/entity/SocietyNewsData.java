package com.gjf.lovezzu.entity;

/**
 * Created by zhao on 2017/8/6.
 */

public class SocietyNewsData {
    private String reason;
    private SocietyNewsResultBridging result;
    private Integer error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public SocietyNewsResultBridging getResult() {
        return result;
    }

    public void setResult(SocietyNewsResultBridging result) {
        this.result = result;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }
}
