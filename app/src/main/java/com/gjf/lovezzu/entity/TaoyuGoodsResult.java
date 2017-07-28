package com.gjf.lovezzu.entity;

import java.util.ArrayList;

/**
 * Created by BlackBeard丶 on 2017/04/13.
 */

public class TaoyuGoodsResult {

    private Integer goods_id ;//商品id
    private String gtype;// 商品分类
    private String gname;//  商品名称
    private String gcampus;// 几年级用品
    private String gdescribe;//商品描述
    private String gprice;// 商品价格
    private String gimage;//商品图片
    private String gsearch;//用于搜索的字段
    private String gdate;
    private String UG_id;
    private boolean isSuccessful;
    private String imageUrl;//头像地址
    private String nickname;//昵称
    private String phone;//手机号
    private String qr_codeUrl; //二维码地址
    private String gender;//性别
    private String hometown ;//家乡
    private String academy;//院校
    private String departments ;//院系
    private String professional ;//专业

    private String Gthumb;//点赞数
    private String Gcomments;//评论数

    public String getGthumb() {
        return Gthumb;
    }

    public void setGthumb(String gthumb) {
        Gthumb = gthumb;
    }

    public String getGcomments() {
        return Gcomments;
    }

    public void setGcomments(String gcomments) {
        Gcomments = gcomments;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGcampus() {
        return gcampus;
    }

    public void setGcampus(String gcampus) {
        this.gcampus = gcampus;
    }

    public String getGdescribe() {
        return gdescribe;
    }

    public void setGdescribe(String gdescribe) {
        this.gdescribe = gdescribe;
    }

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public String getGimage() {
        return gimage;
    }

    public void setGimage(String gimage) {
        this.gimage = gimage;
    }

    public String getGsearch() {
        return gsearch;
    }

    public void setGsearch(String gsearch) {
        this.gsearch = gsearch;
    }

    public String getGdate() {
        return gdate;
    }

    public void setGdate(String gdate) {
        this.gdate = gdate;
    }

    public String getQr_codeUrl() {
        return qr_codeUrl;
    }

    public void setQr_codeUrl(String qr_codeUrl) {
        this.qr_codeUrl = qr_codeUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }



    public String getUG_id() {
        return UG_id;
    }

    public void setUG_id(String UG_id) {
        this.UG_id = UG_id;
    }
}
