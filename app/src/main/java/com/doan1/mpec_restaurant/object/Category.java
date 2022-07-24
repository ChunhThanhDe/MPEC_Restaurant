package com.doan1.mpec_restaurant.object;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private List Dish;

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

    public List getDish() {
        return Dish;
    }

    public void setDish(List dish) {
        Dish = dish;
    }
}
