package com.gjf.lovezzu.entity;

/**
 * Created by zhao on 2017/8/3.
 */

public class GoodsChildCommentsResult {
    private String account;
    private String cdate;
    private String comments;
    private Integer l2_Cid;
    private int num_replies;
    private int num_thumb;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getL2_Cid() {
        return l2_Cid;
    }

    public void setL2_Cid(Integer l2_Cid) {
        this.l2_Cid = l2_Cid;
    }

    public int getNum_replies() {
        return num_replies;
    }

    public void setNum_replies(int num_replies) {
        this.num_replies = num_replies;
    }

    public int getNum_thumb() {
        return num_thumb;
    }

    public void setNum_thumb(int num_thumb) {
        this.num_thumb = num_thumb;
    }
}
