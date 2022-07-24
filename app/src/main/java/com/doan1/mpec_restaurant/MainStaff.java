package com.doan1.mpec_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainStaff extends AppCompatActivity {

    private Button btnManagerCustomer;
    private Button btnManagerStaff;
    private Button btnManagerBill;
    private Button btnManagertabler;
    private Button btnManagerOrder;
    private Button btnManagerMenu;
    private Button btnChangePassword;
    private Button btnInformation;
    private Button btnSignOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_staff);

        btnChangePassword = findViewById(R.id.btn_changepassword);
        btnManagerCustomer = findViewById(R.id.btn_managercustomer);
        btnManagerStaff = findViewById(R.id.btn_managerstaff);
        btnManagerBill = findViewById(R.id.btn_managerbill);
        btnManagertabler = findViewById(R.id.btn_managertable);
        btnManagerOrder = findViewById(R.id.btn_managerorder);
        btnManagerMenu = findViewById(R.id.btn_managermenu);
        btnInformation = findViewById(R.id.btn_information);
        btnSignOut = findViewById(R.id.btn_signout);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainStaff.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainStaff.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}
