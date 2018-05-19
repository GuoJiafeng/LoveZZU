package com.gjf.lovezzu.entity.Message;

import com.gjf.lovezzu.entity.UserInfoResult;

/**
 * Created by aotu on 2017/10/24.
 * 速信
 */

public class MessageItem {
    private String mMessageTime;
    private String mMessageContent;
    private String mMessageAnswer;
    private boolean mBoolean;

    public boolean isBoolean() {
        return mBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        mBoolean = aBoolean;
    }

    private String mMessageChecked;
    private UserInfoResult mUserInfoResult;

    public String getMessageTime() {
        return mMessageTime;
    }

    public void setMessageTime(String messageTime) {
        mMessageTime = messageTime;
    }

    public String getMessageContent() {
        return mMessageContent;
    }

    public void setMessageContent(String messageContent) {
        mMessageContent = messageContent;
    }

    public String getMessageAnswer() {
        return mMessageAnswer;
    }

    public void setMessageAnswer(String messageAnswer) {
        mMessageAnswer = messageAnswer;
    }

    public String getMessageChecked() {
        return mMessageChecked;
    }

    public void setMessageChecked(String messageChecked) {
        mMessageChecked = messageChecked;
    }

    public UserInfoResult getUserInfoResult() {
        return mUserInfoResult;
    }

    public void setUserInfoResult(UserInfoResult userInfoResult) {
        mUserInfoResult = userInfoResult;
    }
}
