package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomItemForPlaceOrder {

    public int id;

    public int price;

    public int quantity;

    public String item_image;



    public CustomItemForPlaceOrder(int id, int price, int quantity, String item_image) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.item_image = item_image;
    }

    public CustomItemForPlaceOrder(int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CustomItemForPlaceOrder{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", item_image='" + item_image + '\'' +
                '}';
    }
}
