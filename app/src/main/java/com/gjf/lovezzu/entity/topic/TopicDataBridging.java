package com.gjf.lovezzu.entity.topic;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/12.
 */

public class TopicDataBridging implements Serializable{
    private TopicResult topic;
    private UserInfoResult userinfo;

    public TopicResult getTopic() {
        return topic;
    }

    public void setTopic(TopicResult topic) {
        this.topic = topic;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
