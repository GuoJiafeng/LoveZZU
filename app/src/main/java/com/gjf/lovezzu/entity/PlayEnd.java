package com.gjf.lovezzu.entity;

/**
 * Created by zhaox on 2017/4/9.
 */

public class PlayEnd extends PlayItems{
    private int groupImage;
    private String groupName;
    private String groupInfo;
    private int groupTagesImages1;
    private int groupTagesImages2;
    private String groupNewsTitle;
    private int infoImages1;
    private int infoImages2;
    private int infoImages3;
    private int joinersImage1;
    private int joinersImage2;
    private int joinersImage3;
    private int joinersImage4;

    public PlayEnd() {
    }



    public PlayEnd(int groupImage, String groupName, String groupInfo, int groupTagesImages1, int groupTagesImages2,
                   String groupNewsTitle, int infoImages1, int infoImages2, int infoImages3, int joinersImage1,
                   int joinersImage2, int joinersImage3, int joinersImage4) {
        this.groupImage = groupImage;
        this.groupInfo = groupInfo;
        this.groupName = groupName;
        this.groupTagesImages1 = groupTagesImages1;
        this.groupTagesImages2 = groupTagesImages2;
        this.groupNewsTitle = groupNewsTitle;
        this.infoImages1 = infoImages1;
        this.infoImages2 = infoImages2;
        this.infoImages3 = infoImages3;
        this.joinersImage1 = joinersImage1;
        this.joinersImage2 = joinersImage2;
        this.joinersImage3 = joinersImage3;
        this.joinersImage4 = joinersImage4;
    }


    public int getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(int groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(String groupInfo) {
        this.groupInfo = groupInfo;
    }

    public int getGroupTagesImages1() {
        return groupTagesImages1;
    }

    public void setGroupTagesImages1(int groupTagesImages1) {
        this.groupTagesImages1 = groupTagesImages1;
    }

    public int getGroupTagesImages2() {
        return groupTagesImages2;
    }

    public void setGroupTagesImages2(int groupTagesImages2) {
        this.groupTagesImages2 = groupTagesImages2;
    }

    public String getGroupNewsTitle() {
        return groupNewsTitle;
    }

    public void setGroupNewsTitle(String groupNewsTitle) {
        this.groupNewsTitle = groupNewsTitle;
    }

    public int getInfoImages1() {
        return infoImages1;
    }

    public void setInfoImages1(int infoImages1) {
        this.infoImages1 = infoImages1;
    }

    public int getInfoImages2() {
        return infoImages2;
    }

    public void setInfoImages2(int infoImages2) {
        this.infoImages2 = infoImages2;
    }

    public int getInfoImages3() {
        return infoImages3;
    }

    public void setInfoImages3(int infoImages3) {
        this.infoImages3 = infoImages3;
    }

    public int getJoinersImage1() {
        return joinersImage1;
    }

    public void setJoinersImage1(int joinersImage1) {
        this.joinersImage1 = joinersImage1;
    }

    public int getJoinersImage2() {
        return joinersImage2;
    }

    public void setJoinersImage2(int joinersImage2) {
        this.joinersImage2 = joinersImage2;
    }

    public int getJoinersImage3() {
        return joinersImage3;
    }

    public void setJoinersImage3(int joinersImage3) {
        this.joinersImage3 = joinersImage3;
    }

    public int getJoinersImage4() {
        return joinersImage4;
    }

    public void setJoinersImage4(int joinersImage4) {
        this.joinersImage4 = joinersImage4;
    }
}
