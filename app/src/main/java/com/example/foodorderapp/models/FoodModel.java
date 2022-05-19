package com.example.foodorderapp.models;

import java.io.Serializable;
import java.util.List;

public class FoodModel implements Serializable {
    private String _id;
    private String name;
    private String description;
    private List images;
    private double price;
    private int numberInCart;

    public FoodModel(String _id, String name, String description, double price) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
}
