package com.doan1.mpec_restaurant;

import android.content.Intent;
import android.os.Bundle;
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

public class LogupActivity extends AppCompatActivity {

    private EditText edtAccount;
    private EditText edtName;
    private EditText edtSex;
    private EditText edtPhoneNumber;
    private EditText edtDateOfBirth;
    private EditText edtAddress;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPassword2;
    private Button btnLoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        edtAccount = findViewById(R.id.edt_account);
        edtName = findViewById(R.id.edt_name);
        edtSex = findViewById(R.id.edt_sex);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        edtDateOfBirth = findViewById(R.id.edt_dateOfBirth);
        edtAddress = findViewById(R.id.edt_address);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtPassword2 = findViewById(R.id.edt_password_2);
        btnLoup = findViewById(R.id.btn_logup);

        btnLoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strPassword = edtPassword.getText().toString().trim();
                    String strPassword2 = edtPassword2.getText().toString().trim();

                    if (strPassword.equals(strPassword2)) {
                        addCustomer();
                    } else {
                        Toast.makeText(LogupActivity.this, "Password does not match!!!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LogupActivity.this, "Phone number can not be left blank.!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addCustomer() {
        Customer customer = new Customer(null,
                edtAccount.getText().toString(),
                edtPassword.getText().toString(),
                edtName.getText().toString(),
                edtDateOfBirth.getText().toString(),
                edtAddress.getText().toString(),
                edtSex.getText().toString(),
                edtEmail.getText().toString(),
                Integer.parseInt(edtPhoneNumber.getText().toString()));


        ApiCustomer.apiCustomer.addCustomer(customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customerResult = response.body();
                if (customerResult != null) {
                    onClickLogupSuccess(customerResult);
                    Toast.makeText(LogupActivity.this, "log up success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LogupActivity.this, customer.toString() + "   Api khong post duocj", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(LogupActivity.this, "Call api errorrrr", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void onClickLogupSuccess(Customer customer) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", customer);
        intent.putExtra("user", 0);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
