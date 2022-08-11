package com.doan1.mpec_restaurant.onClick_interface;

import com.doan1.mpec_restaurant.object.Category;

public interface IClickCardCategoryListener {
    void updateCategory(Category category);

    void deleteCategory(Category category);
}
