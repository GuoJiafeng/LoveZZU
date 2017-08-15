package com.gjf.lovezzu.entity.topic;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhaox on 2017/3/22.
 */

public class TopicThemeBridging implements Serializable{
    private TopicTheme theme;
    private UserInfoResult userinfo;

    public TopicTheme getTheme() {
        return theme;
    }

    public void setTheme(TopicTheme theme) {
        this.theme = theme;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
