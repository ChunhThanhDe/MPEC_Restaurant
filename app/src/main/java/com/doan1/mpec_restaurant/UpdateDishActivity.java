package com.doan1.mpec_restaurant;

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
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.doan1.mpec_restaurant.api.ApiDish;
import com.doan1.mpec_restaurant.api.Const;
import com.doan1.mpec_restaurant.object.Dish;
import com.doan1.mpec_restaurant.widget.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDishActivity extends AppCompatActivity {

    private static final int MY_REQUESTCODE = 10;
    private EditText edtName;
    private EditText edtPrice;
    private EditText edtShortDescription;
    private EditText edtCategoryName;

    private Button btnSelectImage;
    private Button btnUpdateDish;

    private Dish mDish;
    private ImageView imageView;
    private String sImage;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

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
                            imageView.setImageBitmap(bitmap);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] bytes = stream.toByteArray();
                            sImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dish);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait....");

        edtName = findViewById(R.id.edt_name);
        edtPrice = findViewById(R.id.edt_price);
        edtShortDescription = findViewById(R.id.edt_short_description);
        edtCategoryName = findViewById(R.id.edt_category_name);
        imageView = findViewById(R.id.imageView);

        btnUpdateDish = findViewById(R.id.btn_update_dish);
        btnSelectImage = findViewById(R.id.btn_select_image);

        mDish = (Dish) getIntent().getExtras().get("object_dish");
        if (mDish != null) {
            edtName.setText(mDish.getName());
            edtPrice.setText(String.valueOf(mDish.getPrice()));
            edtShortDescription.setText(mDish.getShortDescription());
            edtCategoryName.setText(mDish.getCategoryName());

            byte[] bytes = Base64.decode(mDish.getImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);

        }
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }

        });

        btnUpdateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                    updateDish();
                }
//                updateDish();
            }
        });
    }

    private void updateDish() {
        int idDish = mDish.getId();
        String strName = edtName.getText().toString();

        if (TextUtils.isEmpty(strName)) {
            return;
        }

        //update Dish in database
        mProgressDialog.show();

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

        ApiDish.apiDish.putDish(idDish, requestBodyName, requestBodyPrice, requestBodyShortDescription, requestBodyCategoryName, multipartBodyImage).enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                mProgressDialog.dismiss();
                Toast.makeText(UpdateDishActivity.this, "update Customer successfully", Toast.LENGTH_SHORT).show();
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_OK, intentResult);
                finish();
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(UpdateDishActivity.this, "call api error", Toast.LENGTH_SHORT).show();
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picturre"));

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
}