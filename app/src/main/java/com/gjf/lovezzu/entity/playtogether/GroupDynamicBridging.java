package com.gjf.lovezzu.entity.playtogether;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/24.
 */

public class GroupDynamicBridging implements Serializable {
    private GroupDynamicResult groupDynamic;
    private UserInfoResult userinfo;

    public GroupDynamicResult getGroupDynamic() {
        return groupDynamic;
    }

    public void setGroupDynamic(GroupDynamicResult groupDynamic) {
        this.groupDynamic = groupDynamic;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }
}
