package com.gjf.lovezzu.entity.saylove;

import java.io.Serializable;

/**
 * Created by Leon on 2017/8/15.
 */

public class Saylove implements Serializable{
   private  String  date;
    private  String loveContent;
    private  String  senderName;
    private  String lovedName;
    private int thembCount;
 private Integer loveCardId;
    private loveCardComment loveCardComment;

    public Integer getLoveCardId() {
        return loveCardId;
    }

    public void setLoveCardId(Integer loveCardId) {
        this.loveCardId = loveCardId;
    }

    public com.gjf.lovezzu.entity.saylove.loveCardComment getLoveCardComment() {
        return loveCardComment;
    }

    public void setLoveCardComment(com.gjf.lovezzu.entity.saylove.loveCardComment loveCardComment) {
        this.loveCardComment = loveCardComment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoveContent() {
        return loveContent;
    }

    public void setLoveContent(String loveContent) {
        this.loveContent = loveContent;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getLovedName() {
        return lovedName;
    }

    public void setLovedName(String lovedName) {
        this.lovedName = lovedName;
    }

    public int getThembCount() {
        return thembCount;
    }

    public void setThembCount(int thembCount) {
        this.thembCount = thembCount;
    }
}
