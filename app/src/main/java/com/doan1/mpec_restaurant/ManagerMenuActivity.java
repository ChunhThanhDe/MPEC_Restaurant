package com.doan1.mpec_restaurant;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.adapter.DishAdapter;
import com.doan1.mpec_restaurant.api.ApiDish;
import com.doan1.mpec_restaurant.object.Dish;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardDishListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerMenuActivity extends AppCompatActivity {

    private Button btnAddDish;
    private Button btnCategory;
    private RecyclerView rcvDish;
    private TextView tvDeleteAll;

    private DishAdapter dishAdapter;
    private List<Dish> mlistDish;
    private SearchView searchView;

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        getListDish();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);

        btnAddDish = findViewById(R.id.btn_add_dish);
        btnCategory = findViewById(R.id.btn_category);
        tvDeleteAll = findViewById(R.id.tv_delete_all);

        searchView = findViewById(R.id.searchview);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dishAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dishAdapter.getFilter().filter(newText);
                return false;
            }
        });


        rcvDish = findViewById(R.id.rcv_dishs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDish.setLayoutManager(linearLayoutManager);

        mlistDish = new ArrayList<>();
        getListDish();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvDish.addItemDecoration(itemDecoration);

        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerMenuActivity.this,AddDishActivity.class);
                startActivity(intent);
            }
        });

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ManagerMenuActivity.this, ManagerCategoryActivity.class);
                startActivity(intent);
            }
        });

        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDeleteAllDish();
            }
        });

    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isDishExist(Dish dish) {
        boolean isHas = false;
        for (Dish mDish : mlistDish) {
            if (mDish.getName().equals(dish.getName())) {
                isHas = true;
                break;
            }
        }
        return isHas;
    }

    private void clickUpdateDish(Dish dish) {
        Intent intent = new Intent(ManagerMenuActivity.this, UpdateDishActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_dish", dish);
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
    }

    private void clickDeleteDish(final Dish dish) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete dish")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete Dish
                        ArrayList list = new ArrayList();
                        list.add(dish.getId());
                        ApiDish.apiDish.deleteDish(list).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ManagerMenuActivity.this, "Delete Dish success!!", Toast.LENGTH_SHORT).show();
                                getListDish();
                                Log.e("haha","hehe");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ManagerMenuActivity.this, "Delete Dish error!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickDeleteAllDish() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete All Dish")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete Dish
                        ArrayList list = new ArrayList();
                        for (Dish dish : mlistDish){
                            list.add(dish.getId());
                        }
                        ApiDish.apiDish.deleteDish(list).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ManagerMenuActivity.this, "Delete Dish success!!", Toast.LENGTH_SHORT).show();
                                getListDish();
                                Log.e("haha","hehe");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ManagerMenuActivity.this, "Delete Dish error!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void getListDish() {
        ApiDish.apiDish.getListDish()
                .enqueue(new Callback<List<Dish>>() {
                    @Override
                    public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                        mlistDish = response.body();
                         dishAdapter = new DishAdapter(mlistDish, new IClickCardDishListener() {
                            @Override
                            public void onClickCardDish(Dish dish) {

                            }

                            @Override
                            public void updateDish(Dish dish) {
                                clickUpdateDish(dish);
                            }

                            @Override
                            public void deleteDish(Dish dish) {
                                clickDeleteDish(dish);
                            }
                        });
                        rcvDish.setAdapter(dishAdapter);
                        Log.e("List", mlistDish.size() + "");
                    }

                    @Override
                    public void onFailure(Call<List<Dish>> call, Throwable t) {
                        Toast.makeText(ManagerMenuActivity.this, "call api error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}