package com.gjf.lovezzu.entity;


/**
 * Created by BlackBeard丶 on 2017/03/04.
 */
public class LoginResult {

   private boolean isSuccessful;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    private String phone;
    private String passowrd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }
}
