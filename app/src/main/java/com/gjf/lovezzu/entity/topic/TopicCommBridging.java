package com.gjf.lovezzu.entity.topic;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/15.
 */

public class TopicCommBridging implements Serializable{
    private TopicComment topiccomment;
    private UserInfoResult userinfo;

    public TopicComment getTopiccomment() {
        return topiccomment;
    }

    public void setTopiccomment(TopicComment topiccomment) {
        this.topiccomment = topiccomment;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
