package com.doan1.mpec_restaurant;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.adapter.CustomerAdapter;
import com.doan1.mpec_restaurant.api.ApiCustomer;
import com.doan1.mpec_restaurant.object.Customer;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardCustomerListener;
import com.doan1.mpec_restaurant.object.Customer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerCustomerActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtSex;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtDateOfBirth;
    private EditText edtEmail;
    private EditText edtAccount;
    private EditText edtPassword;

    private Button btnAddCustomer;
    private RecyclerView rcvCustomer;
    private TextView tvDeleteAll;

    private CustomerAdapter customerAdapter;
    private List<Customer> mlistCustomer;
    private SearchView searchView;

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        loadData();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_customer);

        edtName = findViewById(R.id.edt_name);
        edtAccount = findViewById(R.id.edt_account);
        edtAddress = findViewById(R.id.edt_address);
        edtEmail = findViewById(R.id.edt_email);
        edtDateOfBirth = findViewById(R.id.edt_dateOfBirth);
        edtPassword = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone_number);
        edtSex = findViewById(R.id.edt_sex);

        btnAddCustomer = findViewById(R.id.btn_add_customer);
        tvDeleteAll = findViewById(R.id.tv_delete_all);

        searchView = findViewById(R.id.searchview);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        rcvCustomer = findViewById(R.id.rcv_customer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCustomer.setLayoutManager(linearLayoutManager);

        mlistCustomer = new ArrayList<>();
        loadData();

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvCustomer.addItemDecoration(itemDecoration);

        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCustomer();
            }
        });

        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDeleteAllCustomer();
            }
        });

    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isCustomerExist(Customer customer) {
        boolean isHas = false;
        for (Customer mCustomer : mlistCustomer) {
            if (mCustomer.getName().equals(customer.getName())) {
                isHas = true;
                break;
            }
        }
        return isHas;
    }

    private void clickUpdateCustomer(Customer customer) {
        Intent intent = new Intent(ManagerCustomerActivity.this, UpdateCustomerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_customer", customer);
        intent.putExtras(bundle);
        mActivityResultLauncher.launch(intent);
    }

    private void clickDeleteCustomer(final Customer customer) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete customer")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete Customer
                        ArrayList list = new ArrayList();
                        list.add(customer.getId());
                        ApiCustomer.apiCustomer.deleteCustomer(list).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ManagerCustomerActivity.this, "Delete Customer success!!", Toast.LENGTH_SHORT).show();
                                loadData();
                                Log.e("haha", "hehe");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ManagerCustomerActivity.this, "Delete Customer error!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickDeleteAllCustomer() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete All Customer")
                .setMessage("are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete Customer
                        ArrayList list = new ArrayList();
                        for (Customer customer : mlistCustomer) {
                            list.add(customer.getId());
                        }
                        ApiCustomer.apiCustomer.deleteCustomer(list).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ManagerCustomerActivity.this, "Delete customer success!!", Toast.LENGTH_SHORT).show();
                                loadData();
                                Log.e("haha", "hehe");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ManagerCustomerActivity.this, "Delete Customer error!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void loadData() {
        ApiCustomer.apiCustomer.getListCustomer()
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        mlistCustomer = response.body();
                        customerAdapter = new CustomerAdapter(mlistCustomer, new IClickCardCustomerListener() {
                            @Override
                            public void updateCustomer(Customer customer) {
                                clickUpdateCustomer(customer);
                            }

                            @Override
                            public void deleteCustomer(Customer customer) {
                                clickDeleteCustomer(customer);
                            }
                        });
                        rcvCustomer.setAdapter(customerAdapter);
                        Log.e("List", mlistCustomer.size() + "");
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Toast.makeText(ManagerCustomerActivity.this, "call api error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postCustomer() {
        Customer customer = new Customer(null,
                edtAccount.getText().toString(),
                edtPassword.getText().toString(),
                edtName.getText().toString(),
                edtDateOfBirth.getText().toString(),
                edtAddress.getText().toString(),
                edtSex.getText().toString(),
                edtEmail.getText().toString(),
                Integer.parseInt(edtPhone.getText().toString()));

        if (TextUtils.isEmpty(edtName.getText().toString())) {
            return;
        }
        if (isCustomerExist(customer)) {
            Toast.makeText(this, "customer Exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ApiCustomer.apiCustomer.addCustomer(customer)
                    .enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            loadData();
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
                            Toast.makeText(ManagerCustomerActivity.this, "call api error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        edtName.setText("");
        edtAccount.setText("");
        edtPassword.setText("");
        edtPhone.setText("");
        edtDateOfBirth.setText("");
        edtAddress.setText("");
        edtSex.setText("");
        edtEmail.setText("");

        hideSoftKeyboard();
        loadData();
    }
}