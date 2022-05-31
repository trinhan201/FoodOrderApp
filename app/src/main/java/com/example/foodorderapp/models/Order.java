package com.example.foodorderapp.models;

import java.util.ArrayList;

public class Order {
    private ArrayList<OrderItem> orderItems;

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
