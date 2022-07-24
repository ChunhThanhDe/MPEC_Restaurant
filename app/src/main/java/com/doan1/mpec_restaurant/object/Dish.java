package com.doan1.mpec_restaurant.object;

public class  Dish {
    private int id;
    private int image;
    private String name;
    private int price;
    private String shortDescription;
    private String categoryName;

    public Dish(int id, int image, String name, int price, String shortDescription, String categoryName) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.shortDescription = shortDescription;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
