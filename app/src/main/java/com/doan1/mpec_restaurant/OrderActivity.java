package com.doan1.mpec_restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.doan1.mpec_restaurant.fragmentOrder.FragmentChoose;
import com.doan1.mpec_restaurant.fragmentOrder.FragmentOrder;
import com.doan1.mpec_restaurant.object.Dish;

public class OrderActivity extends AppCompatActivity implements FragmentChoose.ISendDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame_search,new FragmentChoose());
        fragmentTransaction.add(R.id.content_frame_order,new FragmentOrder());
        fragmentTransaction.commit();

    }

    @Override
    public void sendData(Dish dish) {
        FragmentOrder fragmentOrder = (FragmentOrder) getSupportFragmentManager().findFragmentById(R.id.content_frame_order);
        fragmentOrder.receiveDish(dish);
    }
}