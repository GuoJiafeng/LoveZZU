package com.gjf.lovezzu.entity;

/**
 * Created by lenovo047 on 2017/3/24.
 */

public class TaoyuResult {
    private int imageID1;
    private int ImageID2;
    private String phone;
    private String title;
    private String position;
    private int price;
    private int time;
    private int zan;
    private int comment;


    public TaoyuResult(int imageID1, int imageID2, String phone, String title, String position, int price, int time, int zan, int comment) {
        this.imageID1 = imageID1;
        ImageID2 = imageID2;
        this.phone = phone;
        this.title = title;
        this.position = position;
        this.price = price;
        this.time = time;
        this.zan = zan;
        this.comment = comment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImageID1() {
        return imageID1;
    }

    public void setImageID1(int imageID1) {
        this.imageID1 = imageID1;
    }

    public int getImageID2() {
        return ImageID2;
    }

    public void setImageID2(int imageID2) {
        ImageID2 = imageID2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

}
