package com.gjf.lovezzu.entity.saylove;

import java.io.Serializable;

/**
 * Created by Leon on 2017/8/16.
 */

public class SayloveReply implements Serializable{
private loveCardComment loveCardComment;

    public com.gjf.lovezzu.entity.saylove.loveCardComment getLoveCardComment() {
        return loveCardComment;
    }

    public void setLoveCardComment(com.gjf.lovezzu.entity.saylove.loveCardComment loveCardComment) {
        this.loveCardComment = loveCardComment;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    private String userImg;

}
