package com.gjf.lovezzu.entity.topic;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/15.
 */

public class TopicComment implements Serializable {
    private String topicComment;
    private Integer topicCommentCount;
    private Integer topicCommentId;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopicComment() {
        return topicComment;
    }

    public void setTopicComment(String topicComment) {
        this.topicComment = topicComment;
    }

    public Integer getTopicCommentCount() {
        return topicCommentCount;
    }

    public void setTopicCommentCount(Integer topicCommentCount) {
        this.topicCommentCount = topicCommentCount;
    }

    public Integer getTopicCommentId() {
        return topicCommentId;
    }

    public void setTopicCommentId(Integer topicCommentId) {
        this.topicCommentId = topicCommentId;
    }
}
