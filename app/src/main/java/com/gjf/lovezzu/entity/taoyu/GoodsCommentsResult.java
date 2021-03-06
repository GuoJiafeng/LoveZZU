package com.gjf.lovezzu.entity.taoyu;

import java.io.Serializable;

/**
 * Created by zhao on 2017/7/31.
 */

public class GoodsCommentsResult implements Serializable{
    private String account;
    private String cdate;
    private String comments;
    private Integer l1_Cid;
    private int num_replies;
    private int num_thumb;
    private Boolean thembed;//点赞记录

    public void setL1_Cid(Integer l1_Cid) {
        this.l1_Cid = l1_Cid;
    }

    public Boolean getThembed() {
        return thembed;
    }

    public void setThembed(Boolean thembed) {
        this.thembed = thembed;
    }

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

    public int getL1_Cid() {
        return l1_Cid;
    }

    public void setL1_Cid(int l1_Cid) {
        this.l1_Cid = l1_Cid;
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
