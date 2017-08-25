package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/24.
 */

public class Dynamic implements Serializable {
    private Integer dynamicId;//群动态id
    private String talk;//说说
    private String	talkImg;//说说的图片
    private int	thembCount;//点赞量
    private int	commentCount;//评论量
    private String	date;//发表时间
    private Boolean thembed;//点赞记录

    public Boolean getThembed() {
        return thembed;
    }

    public void setThembed(Boolean thembed) {
        this.thembed = thembed;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getTalkImg() {
        return talkImg;
    }

    public void setTalkImg(String talkImg) {
        this.talkImg = talkImg;
    }

    public int getThembCount() {
        return thembCount;
    }

    public void setThembCount(int thembCount) {
        this.thembCount = thembCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
