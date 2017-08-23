package com.gjf.lovezzu.entity.treehole;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/17.
 */

public class TreeHoleComm implements Serializable{
    private Integer treeHoleCommentId;
    private String commentContent;
    private String date;

    public Integer getTreeHoleCommentId() {
        return treeHoleCommentId;
    }

    public void setTreeHoleCommentId(Integer treeHoleCommentId) {
        this.treeHoleCommentId = treeHoleCommentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
