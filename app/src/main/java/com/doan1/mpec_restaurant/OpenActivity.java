package com.doan1.mpec_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OpenActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnLogup;
    private TextView tvForgotPassword;
    private TextView tvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        btnLogin = findViewById(R.id.btn_loginn);
        btnLogup = findViewById(R.id.btn_logup);
        tvForgotPassword = findViewById(R.id.tv_forgotpassword);
        tvMenu = findViewById(R.id.tv_menu);

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenActivity.this, MenuActivity.class);
                startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnLogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenActivity.this, LogupActivity.class);
                startActivity(intent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenActivity.this, ForgotPasswordActivity.class );
                startActivity(intent);
            }
        });

    }
}
