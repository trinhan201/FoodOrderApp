package com.example.foodorderapp.models;

public class OrderItem {
    private String product;
    private int quantity;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItem(String product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }
}
