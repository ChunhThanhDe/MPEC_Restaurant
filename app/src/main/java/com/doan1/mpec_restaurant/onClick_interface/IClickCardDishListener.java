package com.doan1.mpec_restaurant.onClick_interface;

import com.doan1.mpec_restaurant.object.Category;
import com.doan1.mpec_restaurant.object.Dish;

public interface IClickCardDishListener {
    void onClickCardDish(Dish dish);

    void updateDish(Dish dish);

    void deleteDish(Dish dish);
}
