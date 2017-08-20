package com.gjf.lovezzu.entity.playtogether;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/20.
 */

public class GroupResult implements Serializable {
    private Integer groupId;//表id
    private String name;//群名字
    private String	picture;//群图片
    private String	introduce;//群介绍
    private String	label;//群标签
    private String	member;//群成员
    private String	date;//创建时间

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
