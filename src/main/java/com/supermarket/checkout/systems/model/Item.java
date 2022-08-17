package com.supermarket.checkout.systems.model;

import lombok.Data;
@Data
public class Item {
    private String itemName;
    private int itemQuantity;
    private float itemPrice;
    public Item(String itemName, int itemQuantity, float itemPrice) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }
}
