package com.doan1.mpec_restaurant;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.adapter.MenuAdapter;
import com.doan1.mpec_restaurant.api.ApiDish;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardDishListener;
import com.doan1.mpec_restaurant.object.Dish;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView rcvDishs;
    private MenuAdapter menuAdapter;
    private SearchView searchView;
    private List<Dish> mListDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rcvDishs = findViewById(R.id.rcv_dishs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDishs.setLayoutManager(linearLayoutManager);

        mListDish = new ArrayList<>();
        getListDishs();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvDishs.addItemDecoration(itemDecoration);
    }

    private void getListDishs() {
        ApiDish.apiDish.getListDish().enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                mListDish = response.body();
                menuAdapter = new MenuAdapter(mListDish,new IClickCardDishListener() {
                    @Override
                    public void onClickCardDish(Dish dish) {
                        onClickGoToInfor(dish);
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

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {

            }
        });
    }
    private void onClickGoToInfor(Dish dish) {
        Intent intent = new Intent(this, DishInformation.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_dish", dish);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}



