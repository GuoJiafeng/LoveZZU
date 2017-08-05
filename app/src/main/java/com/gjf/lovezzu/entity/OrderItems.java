package com.gjf.lovezzu.entity;

import java.io.Serializable;

/**
 * Created by zhao on 2017/8/5.
 */

public class OrderItems implements Serializable{
    private Integer goods_id;
    private Integer count;
    private Integer items_id;

    public Integer getItems_id() {
        return items_id;
    }

    public void setItems_id(Integer items_id) {
        this.items_id = items_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public OrderItems(Integer goods_id,Integer count){
        this.goods_id=goods_id;
        this.count=count;
    }
    public OrderItems(){
        super();
    }

}
