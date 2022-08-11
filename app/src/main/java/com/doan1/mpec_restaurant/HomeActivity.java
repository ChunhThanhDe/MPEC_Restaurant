package com.doan1.mpec_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.doan1.mpec_restaurant.fragmentHome.ChangePasswordFrament;
import com.doan1.mpec_restaurant.fragmentHome.HistoryFrament;
import com.doan1.mpec_restaurant.fragmentHome.HomeFramentCus;
import com.doan1.mpec_restaurant.fragmentHome.HomeFramentStaff;
import com.doan1.mpec_restaurant.fragmentHome.MyProfileFrament;
import com.doan1.mpec_restaurant.object.Customer;
import com.doan1.mpec_restaurant.object.Staff;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_HISTORY = 1;
    public static final int FRAGMENT_MY_PROFILE = 2;
    public static final int FRAGMENT_CHANGE_PASSWORD = 3;

    private int mCurrentFrament = FRAGMENT_HOME;

    private Customer mCustomer;
    private Staff mStaff;
    private DrawerLayout mDrawerLayout;
    private int userRole;

    public Customer getmCustomer() {
        return mCustomer;
    }

    public Staff getmStaff() {
        return mStaff;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //
        Intent intent = getIntent();
        userRole = intent.getIntExtra("user", 0);
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            return;
        }

        if (userRole == 0) {
            replaceFragment(new HomeFramentCus());
            mCustomer = (Customer) bundle.get("object_user");
        } else if (userRole == 1) {
            replaceFragment(new HomeFramentStaff());
            mStaff = (Staff) bundle.get("object_user");
        }
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFrament != FRAGMENT_HOME) {
                if (userRole == 0) {
                    replaceFragment(new HomeFramentCus());
                } else if (userRole == 1) {
                    replaceFragment(new HomeFramentStaff());
                }
                mCurrentFrament = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_histoy) {
            if (mCurrentFrament != FRAGMENT_HISTORY) {
                replaceFragment(new HistoryFrament());
                mCurrentFrament = FRAGMENT_HISTORY;
            }
        } else if (id == R.id.nav_change_password) {
            if (mCurrentFrament != FRAGMENT_CHANGE_PASSWORD) {
                replaceFragment(new ChangePasswordFrament());
                mCurrentFrament = FRAGMENT_CHANGE_PASSWORD;
            }
        } else if (id == R.id.nav_my_profile) {
            if (mCurrentFrament != FRAGMENT_MY_PROFILE) {
                replaceFragment(new MyProfileFrament());
                mCurrentFrament = FRAGMENT_MY_PROFILE;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mCurrentFrament != FRAGMENT_HOME) {
            if (userRole == 0) {
                replaceFragment(new HomeFramentCus());
            } else if (userRole == 1) {
                replaceFragment(new HomeFramentStaff());
            }
        }
        else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}
