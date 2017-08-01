package com.gjf.lovezzu.entity;

/**
 * Created by Leon on 2017/7/30.
 */

public class Shopcart {
    private String good_name;
    private  int price;
    private  String seller_name;
    private  String good_content;
    private  String ImageId;

    public Shopcart(String good_name, int price, String seller_name, String good_content) {
        this.good_name = good_name;
        this.price = price;
        this.seller_name = seller_name;
        this.good_content = good_content;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getGood_content() {
        return good_content;
    }

    public void setGood_content(String good_content) {
        this.good_content = good_content;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }
}
