package com.doan1.mpec_restaurant;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import com.doan1.mpec_restaurant.adapter.CategoryAdapter;
import com.doan1.mpec_restaurant.api.ApiCategory;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardCategoryListener;
import com.doan1.mpec_restaurant.object.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerCategoryActivity extends AppCompatActivity {

    private EditText edtName;
    private Button btnAddCategory;
    private RecyclerView rcvCategory;
    private TextView tvDeleteAll;

    private CategoryAdapter categoryAdapter;
    private List<Category> mlistCategory;
    private SearchView searchView;

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        getListCategory();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_category);

        edtName = findViewById(R.id.edt_name);
        btnAddCategory = findViewById(R.id.btn_add_category);
        tvDeleteAll = findViewById(R.id.tv_delete_all);
        searchView = findViewById(R.id.searchview);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                categoryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoryAdapter.getFilter().filter(newText);
                return false;
            }
        });


        rcvCategory = findViewById(R.id.rcv_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCategory.setLayoutManager(linearLayoutManager);

        mlistCategory = new ArrayList<>();
        getListCategory();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvCategory.addItemDecoration(itemDecoration);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCategory();
            }
        });

        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDeleteAllCategory();
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

    private boolean isCategoryExist(Category category) {
        boolean isHas = false;
        for (Category mCategory : mlistCategory) {
            if (mCategory.getName().equals(category.getName())) {
                isHas = true;
                break;
            }
        }
        return isHas;
    }

    private void clickUpdateCategory(Category category) {
        Intent intent = new Intent(ManagerCategoryActivity.this, UpdateCategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_category", category);
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
    }

    private void clickDeleteCategory(final Category category) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete Category")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete Category
                        ArrayList list = new ArrayList();
                        list.add(category.getId());
                        ApiCategory.apiCategory.deleteCategory(list).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ManagerCategoryActivity.this, "Delete Category success!!", Toast.LENGTH_SHORT).show();
                                getListCategory();
                                Log.e("haha","hehe");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ManagerCategoryActivity.this, "Delete Category error!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickDeleteAllCategory() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete All Category")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete Category
                        ArrayList list = new ArrayList();
                        for (Category category : mlistCategory){
                            list.add(category.getId());
                        }
                        ApiCategory.apiCategory.deleteCategory(list).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ManagerCategoryActivity.this, "Delete Category success!!", Toast.LENGTH_SHORT).show();
                                getListCategory();
                                Log.e("haha","hehe");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ManagerCategoryActivity.this, "Delete Category error!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void getListCategory() {
        ApiCategory.apiCategory.getListCategory()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        mlistCategory = response.body();
                        CategoryAdapter categoryAdapter = new CategoryAdapter(mlistCategory, new IClickCardCategoryListener() {
                            @Override
                            public void updateCategory(Category category) {
                                clickUpdateCategory(category);
                            }

                            @Override
                            public void deleteCategory(Category category) {
                                clickDeleteCategory(category);
                            }
                        });
                        rcvCategory.setAdapter(categoryAdapter);
                        Log.e("List", mlistCategory.size() + "");
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Toast.makeText(ManagerCategoryActivity.this, "call api error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postCategory() {
        String strName = edtName.getText().toString().trim();
        Category category = new Category(null, strName);

        if (TextUtils.isEmpty(strName)) {
            return;
        }
        if (isCategoryExist(category)) {
            Toast.makeText(this, "category Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ApiCategory.apiCategory.postCategory(category)
                    .enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            getListCategory();
                        }
                        @Override
                        public void onFailure(Call<Category> call, Throwable t) {
                            Toast.makeText(ManagerCategoryActivity.this, "call api error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        edtName.setText("");
        hideSoftKeyboard();
        getListCategory();
    }
}