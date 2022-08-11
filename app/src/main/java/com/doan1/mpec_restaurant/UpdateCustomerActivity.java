package com.doan1.mpec_restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doan1.mpec_restaurant.api.ApiCustomer;
import com.doan1.mpec_restaurant.object.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCustomerActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtSex;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtDateOfBirth;
    private EditText edtEmail;
    private EditText edtAccount;
    private EditText edtPassword;

    private Button btnUpdateCustomer;

    private Customer mCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);

        edtName = findViewById(R.id.edt_name);
        edtAccount = findViewById(R.id.edt_account);
        edtAddress = findViewById(R.id.edt_address);
        edtEmail = findViewById(R.id.edt_email);
        edtDateOfBirth = findViewById(R.id.edt_dateOfBirth);
        edtPassword = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone_number);
        edtSex = findViewById(R.id.edt_sex);

        btnUpdateCustomer = findViewById(R.id.btn_update_customer);

        mCustomer = (Customer) getIntent().getExtras().get("object_customer");
        if (mCustomer != null){


            edtName.setText(mCustomer.getName());
            edtAccount.setText(mCustomer.getAccount());
            edtAddress.setText(mCustomer.getAddress());
            edtEmail.setText(mCustomer.getEmail());
            edtDateOfBirth.setText(mCustomer.getDateOfBirth());
            edtPassword.setText(mCustomer.getPassword());
            edtPhone.setText(String.valueOf(mCustomer.getPhoneNumber()));
            edtSex.setText(mCustomer.getSex());
        }
        btnUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer();
            }
        });
    }

    private void updateCustomer() {
        int idCustomer = mCustomer.getId();
        String strName = edtName.getText().toString().trim();

        if (TextUtils.isEmpty(strName)) {
            return;
        }

        //update Customer in database
        mCustomer.setName(strName);

        ApiCustomer.apiCustomer.putCustomer(idCustomer, mCustomer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Toast.makeText(UpdateCustomerActivity.this,"update Customer successfully", Toast.LENGTH_SHORT).show();
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_OK, intentResult);
                finish();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(UpdateCustomerActivity.this,"call api error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}