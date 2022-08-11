package com.doan1.mpec_restaurant;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doan1.mpec_restaurant.adapter.ItemListAdapter;
import com.doan1.mpec_restaurant.api.ApiCategory;
import com.doan1.mpec_restaurant.api.ApiDish;
import com.doan1.mpec_restaurant.api.Const;
import com.doan1.mpec_restaurant.object.Category;
import com.doan1.mpec_restaurant.object.Dish;
import com.doan1.mpec_restaurant.widget.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDishActivity extends AppCompatActivity {

    public static final String TAG = AddDishActivity.class.getName();

    private static final int MY_REQUESTCODE = 10;
    private EditText edtName;
    private EditText edtPrice;
    private EditText edtShortDescription;
    private EditText edtCategoryName;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvShortDescription;
    private TextView tvCategoryName;
    private Button btnSelectImage, btnUpLoadImage, btnBack;
    private ImageView imgFromGallery, imgFromApi;
    private String sImage;
    private RecyclerView rcvCategory;
    private ItemListAdapter itemListAdapter;
    private List<Category> mlistCategory;

    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private void initUi() {
        edtName = findViewById(R.id.edt_name);
        edtPrice = findViewById(R.id.edt_price);
        edtShortDescription = findViewById(R.id.edt_short_description);
        edtCategoryName = findViewById(R.id.edt_category_name);
        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvShortDescription = findViewById(R.id.tv_short_description);
        tvCategoryName = findViewById(R.id.tv_category_name);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnUpLoadImage = findViewById(R.id.btn_upload_imaga);
        imgFromGallery = findViewById(R.id.img_from_gallery);
        imgFromApi = findViewById(R.id.img_from_api);
        btnBack = findViewById(R.id.btn_back);

    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgFromGallery.setImageBitmap(bitmap);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                            byte[] bytes = stream.toByteArray();
                            sImage = Base64.encodeToString(bytes,Base64.DEFAULT);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        initUi();

        rcvCategory = findViewById(R.id.rcv_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCategory.setLayoutManager(linearLayoutManager);

        mlistCategory = new ArrayList<>();
        getListCategory();

        //init progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait....");

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }

        });
        btnUpLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                    callApiAddDish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDishActivity.this, ManagerMenuActivity.class);
                startActivity(intent);
            }
        });
    }


    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUESTCODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picturre"));

    }

    private void callApiAddDish() {
        mProgressDialog.show();

        String strName = edtName.getText().toString();
        int strPrice = Integer.parseInt(edtPrice.getText().toString());
        String strShortDescription = edtShortDescription.getText().toString();
        String strCategoryName = edtCategoryName.getText().toString();

        RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/from-data"), strName);
        RequestBody requestBodyPrice = RequestBody.create(MediaType.parse("multipart/from-data"), String.valueOf(strPrice));
        RequestBody requestBodyShortDescription = RequestBody.create(MediaType.parse("multipart/from-data"), strShortDescription);
        RequestBody requestBodyCategoryName = RequestBody.create(MediaType.parse("multipart/from-data"), strCategoryName);

        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        Log.e("Dish", strRealPath);
        File file = new File(strRealPath);
        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/from-data"), file);

        MultipartBody.Part multipartBodyImage = MultipartBody.Part.createFormData(Const.KEY_DISH_IMAGE, file.getName(), requestBodyImage);

        ApiDish.apiDish.postDish(requestBodyName, requestBodyPrice, requestBodyShortDescription, requestBodyCategoryName, multipartBodyImage).enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                mProgressDialog.dismiss();
                Dish dish = response.body();
                if (dish == null){
                    Toast.makeText(AddDishActivity.this, "body null", Toast.LENGTH_SHORT).show();
                }
                if (dish != null) {
                    tvName.setText(dish.getName());
                    tvPrice.setText(String.valueOf(dish.getPrice()));
                    tvShortDescription.setText(dish.getShortDescription());
                    tvCategoryName.setText(dish.getCategoryName());

                    byte[] bytes = Base64.decode(dish.getImage(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgFromApi.setImageBitmap(bitmap);
//                    Glide.with(AddDishActivity.this).load(dish.getImage()).into(imgFromApi);
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(AddDishActivity.this, "call api fail", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void getListCategory() {
        ApiCategory.apiCategory.getListCategory()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        mlistCategory = response.body();
                        ItemListAdapter categoryAdapter = new ItemListAdapter(mlistCategory);
                        rcvCategory.setAdapter(categoryAdapter);
                        Log.e("List", mlistCategory.size() + "");
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Toast.makeText(AddDishActivity.this, "call api error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}