package com.gjf.lovezzu.entity;

/**
 * Created by zhaox on 2017/4/9.
 */

public class PlayTop extends PlayItems{
    private int userImage;
    private String userName;
    private String dateTime;
    private String nice;
    private String talk;
    private String userTitle;
    private String newsTitle;
    private int newsImage;

    public PlayTop() {

    }

    public PlayTop(int userImage, String userName, String dateTime, String nice,
                   String talk, String userTitle, String newsTitle, int newsImage) {
        this.userImage = userImage;
        this.userName = userName;
        this.dateTime = dateTime;
        this.nice = nice;
        this.talk = talk;
        this.userTitle = userTitle;
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
    }


    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userNanme) {
        this.userName = userNanme;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNice() {
        return nice;
    }

    public void setNice(String nice) {
        this.nice = nice;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }
}
