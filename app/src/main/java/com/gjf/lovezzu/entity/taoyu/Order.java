package com.gjf.lovezzu.entity.taoyu;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/4.
 */

public class Order implements Serializable{
    private String address;
    private String buyerOrseller;
    private String name;
    private String order_date;
    private Integer order_id;
    private String order_status;
    private String phone;
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuyerOrseller() {
        return buyerOrseller;
    }

    public void setBuyerOrseller(String buyerOrseller) {
        this.buyerOrseller = buyerOrseller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Order(   String address,String buyerOrseller,String name, String order_date,Integer order_id, String order_status, String phone){
        this.address=address;
        this.buyerOrseller=buyerOrseller;
        this.name=name;
        this.order_date=order_date;
        this.order_id=order_id;
        this.order_status=order_status;
        this.phone=phone;
    }

    public Order(){
        super();
    }
}
