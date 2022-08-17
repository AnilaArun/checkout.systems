package com.supermarket.checkout.systems.model;

import lombok.Data;
@Data
public class ItemOnOffer {
    private String itemName;
    private int quantity;
    private double price;

    public ItemOnOffer(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
