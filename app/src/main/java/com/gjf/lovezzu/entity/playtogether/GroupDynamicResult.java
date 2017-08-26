package com.gjf.lovezzu.entity.playtogether;

import com.gjf.lovezzu.entity.UserInfoResult;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/20.
 */

public class GroupDynamicResult implements Serializable {

    private Dynamic groupDynamic;
    private UserInfoResult userinfo;

    public Dynamic getGroupDynamic() {
        return groupDynamic;
    }

    public void setGroupDynamic(Dynamic groupDynamic) {
        this.groupDynamic = groupDynamic;
    }

    public UserInfoResult getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoResult userinfo) {
        this.userinfo = userinfo;
    }


}
