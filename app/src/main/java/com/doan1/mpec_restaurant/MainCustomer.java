package com.doan1.mpec_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainCustomer extends AppCompatActivity {

    private Button btnMenu;
    private Button btnOrder;
    private Button btnManagerBill;
    private Button btnTable;
    private Button btnChangePassword;
    private Button btnInformation;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_customer);

        btnMenu = findViewById(R.id.btn_menu);
        btnOrder = findViewById(R.id.btn_order);
        btnManagerBill = findViewById(R.id.btn_managerbill);
        btnChangePassword = findViewById(R.id.btn_changepassword);
        btnTable = findViewById(R.id.btn_table);
        btnInformation = findViewById(R.id.btn_information);
        btnSignOut = findViewById(R.id.btn_signout);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, InformationActivity.class);
                startActivity(intent);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, OrderActivity.class);
                startActivity(intent);
            }
        });
        btnManagerBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCustomer.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
