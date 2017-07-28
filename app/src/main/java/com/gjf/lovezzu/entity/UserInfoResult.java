package com.gjf.lovezzu.entity;

/**
 * Created by BlackBeard丶 on 2017/03/09.
 */
public class UserInfoResult {
    private boolean isSuccessful;
    private int info_id;
    private String qr_codeUrl;
    private String imageUrl;//头像地址
    private String nickname;//昵称
    private String phone;//手机号
    private String gender;//性别
    private String hometown;//家乡
    private String academy;//院校
    private String departments;//院系
    private String professional;//专业

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }


    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getQr_codeUrl() {
        return qr_codeUrl;
    }

    public void setQr_codeUrl(String qr_codeUrl) {
        this.qr_codeUrl = qr_codeUrl;
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
}
