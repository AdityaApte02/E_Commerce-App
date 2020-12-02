package com.example.mini_project_phone_auth;

import java.io.Serializable;

public class Seller implements Serializable {

    private String sellerName;
    private String itemName;
    private double SellerPrice;
    private int quantity;
    private String phone;


    public Seller(){

    }

    public Seller(String sellerName, String itemName, double SellerPrice, int quantity,String phone) {
        this.sellerName = sellerName;
        this.itemName = itemName;
        this.SellerPrice = SellerPrice;
        this.quantity=quantity;
        this.phone=phone;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getSellerPrice() {
        return SellerPrice;
    }

    public void setSellerPrice(double sellerPrice) {
        this.SellerPrice = sellerPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerName='" + sellerName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", SellerPrice=" + SellerPrice +
                ", quantity=" + quantity +
                ", phone='" + phone + '\'' +
                '}';
    }
}
