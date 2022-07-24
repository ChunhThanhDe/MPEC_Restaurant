package com.doan1.mpec_restaurant;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.adapter.DishOrderAdapter;
import com.doan1.mpec_restaurant.object.Dish;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView rcvDishs;
    private DishOrderAdapter dishOrderAdapter;
    private SearchView searchView;

    private BottomNavigationView bottomNavigationView;
    private TextView tvNumber;
    private Button btnBack;
    private Button btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_menu);

//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        btnBack  = findViewById(R.id.btn_back);
//        btnOrder = findViewById(R.id.btn_order);


        rcvDishs = findViewById(R.id.rcv_dishs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDishs.setLayoutManager(linearLayoutManager);

        dishOrderAdapter = new DishOrderAdapter(getListDishs());
        rcvDishs.setAdapter(dishOrderAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvDishs.addItemDecoration(itemDecoration);

    }


    private List<Dish> getListDishs() {
       List<Dish> list = new ArrayList<>();
       list.add(new Dish(1, R.drawable.bundau,"bun dau",12345,"ada","adad"));
       list.add(new Dish(1, R.drawable.pikalong,"tin",12345,"ada","adad"));
       list.add(new Dish(1, R.drawable.welcome,"tinry",12345,"ada","adad"));
       list.add(new Dish(1, R.drawable.ic_launcher_background,"trtyrtyin",12345,"ada","adad"));


        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dishOrderAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dishOrderAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}


