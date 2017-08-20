package com.gjf.lovezzu.entity.saylove;

import java.io.Serializable;

/**
 * Created by Leon on 2017/8/19.
 */

public class  loveCardComment  implements Serializable {
    private String commentContent;

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

    private  String date;
}