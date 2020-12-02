package com.example.mini_project_phone_auth;

public class Stock {

    private User user;
    private String stockName;
    private double price;
    private int quantity;
    private int stockId;

    public Stock(){

    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "user=" + user +
                ", stockName='" + stockName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", stockId=" + stockId +
                '}';
    }
}
