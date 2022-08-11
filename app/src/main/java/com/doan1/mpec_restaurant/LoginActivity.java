package com.doan1.mpec_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doan1.mpec_restaurant.object.Customer;
import com.doan1.mpec_restaurant.api.ApiCustomer;
import com.doan1.mpec_restaurant.api.ApiManager;
import com.doan1.mpec_restaurant.api.ApiStaff;
import com.doan1.mpec_restaurant.object.Manager;
import com.doan1.mpec_restaurant.object.Staff;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private RadioButton rbtnCustomer;
    private RadioButton rbtnStaff;
    private RadioButton rbtnMannager;

    private List<Staff> mListStaff;
    private List<Customer> mListCustomer;
    private List<Manager> mListManager;

    private Staff mStaff;
    private Manager mManager;
    private Customer mCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        rbtnCustomer = findViewById(R.id.rbtn_customer);
        rbtnMannager = findViewById(R.id.rbtn_manager);
        rbtnStaff = findViewById(R.id.rbtn_staff);

        mListStaff = new ArrayList<>();
        mListManager = new ArrayList<>();
        mListCustomer = new ArrayList<>();

        getListStaff();
        getListCustomer();
        getListManager();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbtnCustomer.isChecked()) {
                    clickLoginCustomer();
                } else if (rbtnStaff.isChecked()){
                    clickLoginStaff();;
                } else if (rbtnMannager.isChecked()){
                    clickLoginManager();
                }
            }
        });
    }


    private void clickLoginStaff() {
        String strUsername = edtUsername.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if (mListStaff == null || mListStaff.isEmpty()) {
            return;
        }
        boolean isHasStaff = false;
        for (Staff staff : mListStaff) {
            if (strUsername.equals(staff.getAccount()) && strPassword.equals(staff.getPassword())) {
                isHasStaff = true;
                mStaff = staff;
                break;
            }
        }

        if (isHasStaff) {
            Intent intent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_user", mStaff);
            intent.putExtra("user",1);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Username or Password is invalid", Toast.LENGTH_SHORT).show();
        }

    }

    private void getListStaff() {
        ApiStaff.apiStaff.getListStaff()
                .enqueue(new Callback<List<Staff>>() {
                    @Override
                    public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                        mListStaff = response.body();
                        Log.e("List Staff", mListStaff.size() + "");

                    }

                    @Override
                    public void onFailure(Call<List<Staff>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "call api error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void clickLoginCustomer() {
        String strUsername = edtUsername.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if (mListCustomer == null || mListCustomer.isEmpty()) {
            return;
        }
        boolean isHasUser = false;
        for (Customer customer : mListCustomer) {
            if (strUsername.equals(customer.getAccount()) && strPassword.equals(customer.getPassword())) {
                isHasUser = true;
                mCustomer = customer;
                break;
            }
        }

        if (isHasUser) {
            Intent intent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_user", mCustomer);
            intent.putExtra("user",0);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Username or Password is invalid", Toast.LENGTH_SHORT).show();
        }

    }

    private void getListCustomer() {
        ApiCustomer.apiCustomer.getListCustomer()
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        mListCustomer = response.body();
                        Log.e("List Customer", mListCustomer.size() + "");
                        Toast.makeText(LoginActivity.this, "call api fly", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "call api errorrrrrr", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void clickLoginManager() {
        String strUsername = edtUsername.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if (mListManager == null || mListManager.isEmpty()) {
            return;
        }
        boolean isHasUser = false;
        for (Manager manager : mListManager) {
            if (strUsername.equals(manager.getAccount()) && strPassword.equals(manager.getPassword())) {
                isHasUser = true;
                mManager = manager;
                break;
            }
        }

        if (isHasUser = true) {
            Intent intent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_user", mManager);
            intent.putExtra("user",1);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            Toast.makeText(LoginActivity.this, "Username or Password is invalid", Toast.LENGTH_SHORT).show();
        }

    }

    private void getListManager() {
        ApiManager.API_MANAGER.getListManager()
                .enqueue(new Callback<List<Manager>>() {
                    @Override
                    public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                        mListManager = response.body();
                        Log.e("List Staff", mListManager.size() + "");

                    }

                    @Override
                    public void onFailure(Call<List<Manager>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "call api error", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}

