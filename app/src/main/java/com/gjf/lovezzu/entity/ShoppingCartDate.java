package com.gjf.lovezzu.entity;

import java.util.List;

/**
 * Created by zhao on 2017/8/2.
 */

public class ShoppingCartDate {
    private String result;
    private List<ShoppingCartDateBridging> shoppingCartDateBridgings;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ShoppingCartDateBridging> getShoppingCartDateBridgings() {
        return shoppingCartDateBridgings;
    }

    public void setShoppingCartDateBridgings(List<ShoppingCartDateBridging> shoppingCartDateBridgings) {
        this.shoppingCartDateBridgings = shoppingCartDateBridgings;
    }
}
