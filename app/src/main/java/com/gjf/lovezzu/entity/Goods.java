package com.gjf.lovezzu.entity;

import java.io.Serializable;

/**
 * Created by zhao on 2017/7/30.
 */

public class Goods implements Serializable {
    private String gcampus;
    private String gname;
    private String gprice;
    private String gdescribe;
    private Integer goods_id;
    private String images[];

    public String getGcampus() {
        return gcampus;
    }

    public void setGcampus(String gcampus) {
        this.gcampus = gcampus;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public String getGdescribe() {
        return gdescribe;
    }

    public void setGdescribe(String gdescribe) {
        this.gdescribe = gdescribe;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
