package com.doan1.mpec_restaurant.object;

public class Review {
    private int id;
    private int star;
    private String comment;
    private int customer_id;
    private int dish_id;

    public Review(int id, int star, String comment, int customer_id, int dish_id) {
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.customer_id = customer_id;
        this.dish_id = dish_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }
}
