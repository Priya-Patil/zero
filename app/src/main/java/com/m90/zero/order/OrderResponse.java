package com.m90.zero.order;

public class OrderResponse {
    int img;
    String title;
    String amount;
    String status;
    String deliverytime;
    String pay_status;
    String date;

    public OrderResponse(int img, String title, String amount, String status, String deliverytime, String pay_status, String date) {
        this.img = img;
        this.title = title;
        this.amount = amount;
        this.status = status;
        this.deliverytime = deliverytime;
        this.pay_status = pay_status;
        this.date = date;
    }

    public OrderResponse() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
