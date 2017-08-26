package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/25.
 */

public class CommentDynamic implements Serializable {
    private String comment;
    private String date;
    private Integer groupDynamicCommentId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getGroupDynamicCommentId() {
        return groupDynamicCommentId;
    }

    public void setGroupDynamicCommentId(Integer groupDynamicCommentId) {
        this.groupDynamicCommentId = groupDynamicCommentId;
    }
}
