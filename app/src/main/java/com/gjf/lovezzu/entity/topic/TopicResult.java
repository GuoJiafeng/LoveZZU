package com.gjf.lovezzu.entity.topic;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/12.
 */

public class TopicResult implements Serializable{
    private Integer topicCommentCount;
    private Integer topicId;
    private String topicImg;
    private String topicText;
    private Integer topicThumbCount;
    private String topicTitle;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTopicCommentCount() {
        return topicCommentCount;
    }

    public void setTopicCommentCount(Integer topicCommentCount) {
        this.topicCommentCount = topicCommentCount;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public Integer getTopicThumbCount() {
        return topicThumbCount;
    }

    public void setTopicThumbCount(Integer topicThumbCount) {
        this.topicThumbCount = topicThumbCount;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }
}
