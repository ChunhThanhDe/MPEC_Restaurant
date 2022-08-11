package com.doan1.mpec_restaurant.fragmentOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.adapter.MenuAdapter;
import com.doan1.mpec_restaurant.object.Dish;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardDishListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentOrder extends Fragment {

    private RecyclerView rcvDishs;
    private MenuAdapter menuAdapter;
    private List<Dish> mListDish;
    private Button btnOrder;

    private View mView;

    public FragmentOrder() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_order, container, false);

        mListDish = new ArrayList<>();

        rcvDishs = mView.findViewById(R.id.rcv_dishs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDishs.setLayoutManager(linearLayoutManager);


        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvDishs.addItemDecoration(itemDecoration);

        return mView;
    }

    public void receiveDish(Dish dish){
        mListDish.add(dish);
        menuAdapter = new MenuAdapter(mListDish, new IClickCardDishListener() {
            @Override
            public void onClickCardDish(Dish dish) {
                deleteDishListOrder(dish);
            }

            @Override
            public void updateDish(Dish dish) {

            }

            @Override
            public void deleteDish(Dish dish) {

            }
        });
        rcvDishs.setAdapter(menuAdapter);
    }

    private void deleteDishListOrder(Dish dish) {
        mListDish.remove(dish);
        menuAdapter = new MenuAdapter(mListDish, new IClickCardDishListener() {
            @Override
            public void onClickCardDish(Dish dish) {
                deleteDishListOrder(dish);
            }

            @Override
            public void updateDish(Dish dish) {

            }

            @Override
            public void deleteDish(Dish dish) {

            }
        });
        rcvDishs.setAdapter(menuAdapter);
    }
}