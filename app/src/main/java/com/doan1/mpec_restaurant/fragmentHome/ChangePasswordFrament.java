package com.doan1.mpec_restaurant.fragmentHome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doan1.mpec_restaurant.HomeActivity;
import com.doan1.mpec_restaurant.LoginActivity;
import com.doan1.mpec_restaurant.ManagerCustomerActivity;
import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.UpdateCustomerActivity;
import com.doan1.mpec_restaurant.api.ApiCustomer;
import com.doan1.mpec_restaurant.object.Customer;
import com.doan1.mpec_restaurant.object.Staff;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFrament extends Fragment {

    private List<Customer> mListCustomer;


    private Button btnSaveChange;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtNewPassword;
    private EditText edtRePassword;

    private Customer mCustomer;
    private HomeActivity mHomeActivity;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_change_password, container, false);

        mHomeActivity = (HomeActivity) getActivity();
        mCustomer = mHomeActivity.getmCustomer();


        btnSaveChange = mView.findViewById(R.id.btn_saveinformation);
        edtUsername = mView.findViewById(R.id.edt_username);
        edtPassword = mView.findViewById(R.id.edt_password);
        edtNewPassword = mView.findViewById(R.id.edt_newpassword);
        edtRePassword = mView.findViewById(R.id.edt_renewpassword);

        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUsername = edtUsername.getText().toString();
                String strPassword = edtPassword.getText().toString();
                String strNewPassword = edtNewPassword.getText().toString();
                String strRePassword = edtRePassword.getText().toString();
                if (strUsername.equals(mCustomer.getAccount()) && strPassword.equals(mCustomer.getPassword())){
                    if(strNewPassword.equals(strRePassword)){
                        mCustomer.setPassword(strNewPassword);
                        putPassword();
                    } else {
                        Toast.makeText(getActivity(), "Password not same", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Username or Password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mView;
    }

    private void putPassword() {
        int idCustomer = mCustomer.getId();
        ApiCustomer.apiCustomer.putCustomer(idCustomer, mCustomer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Change password success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(getActivity(), "Call Api error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
