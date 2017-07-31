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


    public String getUG_id() {
        return UG_id;
    }

    public void setUG_id(String UG_id) {
        this.UG_id = UG_id;
    }
}
