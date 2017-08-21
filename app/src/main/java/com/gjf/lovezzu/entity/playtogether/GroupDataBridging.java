package com.gjf.lovezzu.entity.playtogether;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/21.
 */

public class GroupDataBridging implements Serializable {
    private GroupResult group;
    private List<UserInfoResult> memberInfo;
    private String talkImg;
    private UserInfoResult userinfo;

    public GroupResult getGroup() {
        return group;
    }

    public void setGroup(GroupResult group) {
        this.group = group;
    }

    public List<UserInfoResult> getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(List<UserInfoResult> memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getTalkImg() {
        return talkImg;
    }

    public void setTalkImg(String talkImg) {
        this.talkImg = talkImg;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
