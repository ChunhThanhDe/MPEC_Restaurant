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

import com.doan1.mpec_restaurant.HomeActivity;
import com.doan1.mpec_restaurant.OpenActivity;
import com.doan1.mpec_restaurant.MenuActivity;
import com.doan1.mpec_restaurant.OrderActivity;
import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.object.Customer;

public class HomeFramentCus extends Fragment {

    private View mView;

    private Button btnMenu;
    private Button btnOrder;
    private Button btnManagerBill;
    private Button btnTable;
    private Button btnChangePassword;
    private Button btnInformation;
    private Button btnSignOut;

    private Customer mCustomer;
    private HomeActivity mHomeActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_cus, container, false);

        mHomeActivity = (HomeActivity) getActivity();
        mCustomer = mHomeActivity.getmCustomer();

        btnMenu = mView.findViewById(R.id.btn_menu);
        btnOrder = mView.findViewById(R.id.btn_order);
        btnManagerBill = mView.findViewById(R.id.btn_managerbill);
        btnTable = mView.findViewById(R.id.btn_table);
        btnSignOut = mView.findViewById(R.id.btn_signout);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
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
