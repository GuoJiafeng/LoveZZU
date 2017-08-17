package com.gjf.lovezzu.entity.treehole;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/17.
 */

public class TreeHole implements Serializable{
    private Integer treeHoleId;
    private String  treeHoleContent;
    private String campus;
    private int thembCount;
    private int commentCount;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTreeHoleId() {
        return treeHoleId;
    }

    public void setTreeHoleId(Integer treeHoleId) {
        this.treeHoleId = treeHoleId;
    }

    public String getTreeHoleContent() {
        return treeHoleContent;
    }

    public void setTreeHoleContent(String treeHoleContent) {
        this.treeHoleContent = treeHoleContent;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
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
}
