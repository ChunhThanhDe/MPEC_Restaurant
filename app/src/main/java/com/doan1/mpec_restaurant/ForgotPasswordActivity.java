package com.doan1.mpec_restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doan1.mpec_restaurant.api.ApiCustomer;
import com.doan1.mpec_restaurant.object.Customer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private List<Customer> mListCustomer;
    private Customer mCustomer;

    private EditText edtAccount;
    private EditText edtEmail;
    private TextView tvPassword;
    private Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtAccount = findViewById(R.id.edt_account);
        edtEmail = findViewById(R.id.edt_email);
        tvPassword = findViewById(R.id.tv_password);
        btnEnter = findViewById(R.id.btn_enter);

        mListCustomer = new ArrayList<>();
        getListCustomer();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEnter();
            }
        });


    }
    private void clickEnter() {
        String strAccount = edtAccount.getText().toString().trim();
        String strEmail = edtEmail.getText().toString().trim();

        if (mListCustomer == null || mListCustomer.isEmpty()) {
            return;
        }
        boolean isHasUser = false;
        for (Customer customer : mListCustomer) {
            if (strAccount.equals(customer.getAccount()) && strEmail.equals(customer.getEmail())) {
                isHasUser = true;
                mCustomer = customer;
                break;
            }
        }

        if (isHasUser) {
            tvPassword.setText(mCustomer.getPassword());
        } else {
            Toast.makeText(ForgotPasswordActivity.this, "Username or Email is invalid", Toast.LENGTH_SHORT).show();
        }

    }

    private void getListCustomer() {
        ApiCustomer.apiCustomer.getListCustomer()
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        mListCustomer = response.body();
                        Log.e("List Customer", mListCustomer.size() + "");
                        Toast.makeText(ForgotPasswordActivity.this, "call api fly", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Toast.makeText(ForgotPasswordActivity.this, "call api errorrrrrr", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
