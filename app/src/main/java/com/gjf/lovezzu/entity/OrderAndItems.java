package com.gjf.lovezzu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderAndItems implements Serializable{
    private String action;
    private String SeeionID;
    private Order OrderData;
    private List<OrderItems> OrderItemsData;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSeeionID() {
        return SeeionID;
    }

    public void setSeeionID(String seeionID) {
        SeeionID = seeionID;
    }

    public Order getOrderData() {
        return OrderData;
    }

    public void setOrderData(Order orderData) {
        OrderData = orderData;
    }

    public List<OrderItems> getOrderItemsData() {
        return OrderItemsData;
    }

    public void setOrderItemsData(List<OrderItems> orderItemsData) {
        OrderItemsData = orderItemsData;
    }
}
