package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/20.
 */

public class GroupDynamicReault implements Serializable {
    private Integer dynamicId;//群动态id
    private String talk;//说说
    private String	talkImg;//说说的图片
    private String	thembCount;//点赞量
    private String	commentCount;//评论量
    private String	date;//发表时间

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

    public String getThembCount() {
        return thembCount;
    }

    public void setThembCount(String thembCount) {
        this.thembCount = thembCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
