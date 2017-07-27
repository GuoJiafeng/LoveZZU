package com.gjf.lovezzu.entity;

/**
 * Created by BlackBeard丶 on 2017/04/13.
 */

public class TaoyuGoodsResult {
    private Integer Goods_id ;//商品id
    private String Gtype;// 商品分类
    private String Gname;//  商品名称
    private String Gcampus;// 几年级用品
    private String Gdescribe;//商品描述
    private String Gprice;// 商品价格
    private String Gimage;//商品图片
    private String Gsearch;//用于搜索的字段
    private String Gdate;
    private String UG_id;
    private boolean isSuccessful;
    private String imageUrl;//头像地址
    private String nickname;//昵称
    private String phone;//手机号
    private String Qr_codeUrl; //二维码地址
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

    public String getQr_codeUrl() {
        return Qr_codeUrl;
    }

    public void setQr_codeUrl(String qr_codeUrl) {
        Qr_codeUrl = qr_codeUrl;
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

    public Integer getGoods_id() {
        return Goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        Goods_id = goods_id;
    }

    public String getGtype() {
        return Gtype;
    }

    public void setGtype(String gtype) {
        Gtype = gtype;
    }

    public String getGname() {
        return Gname;
    }

    public void setGname(String gname) {
        Gname = gname;
    }

    public String getGcampus() {
        return Gcampus;
    }

    public void setGcampus(String gcampus) {
        Gcampus = gcampus;
    }

    public String getGdescribe() {
        return Gdescribe;
    }

    public void setGdescribe(String gdescribe) {
        Gdescribe = gdescribe;
    }

    public String getGprice() {
        return Gprice;
    }

    public void setGprice(String gprice) {
        Gprice = gprice;
    }

    public String getGimage() {
        return Gimage;
    }

    public void setGimage(String gimage) {
        Gimage = gimage;
    }

    public String getGsearch() {
        return Gsearch;
    }

    public void setGsearch(String gsearch) {
        Gsearch = gsearch;
    }

    public String getGdate() {
        return Gdate;
    }

    public void setGdate(String gdate) {
        Gdate = gdate;
    }

    public String getUG_id() {
        return UG_id;
    }

    public void setUG_id(String UG_id) {
        this.UG_id = UG_id;
    }
}
