package com.doan1.mpec_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doan1.mpec_restaurant.api.ApiCategory;
import com.doan1.mpec_restaurant.object.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCategoryActivity extends AppCompatActivity {

    private EditText edtName;
    private Button btnUpdateCategory;

    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        edtName = findViewById(R.id.edt_name);
        btnUpdateCategory = findViewById(R.id.btn_update_category);

        mCategory = (Category) getIntent().getExtras().get("object_category");
        if (mCategory != null){
            edtName.setText(mCategory.getName());
        }
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategory();
            }
        });
    }

    private void updateCategory() {
        int idCategory = mCategory.getId();
        String strName = edtName.getText().toString().trim();

        if (TextUtils.isEmpty(strName)) {
            return;
        }

        //update category in database
        mCategory.setName(strName);

        ApiCategory.apiCategory.putCategory(idCategory, mCategory).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Toast.makeText(UpdateCategoryActivity.this,"update category successfully", Toast.LENGTH_SHORT).show();
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_OK, intentResult);
                finish();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(UpdateCategoryActivity.this,"call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}