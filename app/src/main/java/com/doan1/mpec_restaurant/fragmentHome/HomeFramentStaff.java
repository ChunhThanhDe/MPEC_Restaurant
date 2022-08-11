package com.doan1.mpec_restaurant.fragmentHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doan1.mpec_restaurant.ManagerCustomerActivity;
import com.doan1.mpec_restaurant.ManagerMenuActivity;
import com.doan1.mpec_restaurant.OpenActivity;
import com.doan1.mpec_restaurant.R;

public class HomeFramentStaff extends Fragment {

    private View mView;

    private Button btnManagerCustomer;
    private Button btnManagerStaff;
    private Button btnManagerBill;
    private Button btnManagertabler;
    private Button btnManagerOrder;
    private Button btnManagerMenu;
    private Button btnSignOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_staff, container, false);

        btnManagerCustomer = mView.findViewById(R.id.btn_managercustomer);
        btnManagerStaff = mView.findViewById(R.id.btn_managerstaff);
        btnManagerBill = mView.findViewById(R.id.btn_managerbill);
        btnManagertabler = mView.findViewById(R.id.btn_managertable);
        btnManagerOrder = mView.findViewById(R.id.btn_managerorder);
        btnManagerMenu = mView.findViewById(R.id.btn_managermenu);
        btnSignOut = mView.findViewById(R.id.btn_signout);

        btnManagerCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManagerCustomerActivity.class);
                startActivity(intent);
            }
        });

        btnManagerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManagerMenuActivity.class);
                startActivity(intent);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpenActivity.class);
                startActivity(intent);
            }
        });
        return mView;
    }
}

