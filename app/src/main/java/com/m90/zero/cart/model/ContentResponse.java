package com.m90.zero.cart.model;

import com.google.gson.annotations.SerializedName;

public class ContentResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

     @SerializedName("price")
    public int price;

     @SerializedName("quantity")
    public int quantity;



    @Override
    public String toString() {
        return "ContentResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
