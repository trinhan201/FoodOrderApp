package com.example.foodorderapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.foodorderapp.R;
import com.example.foodorderapp.fragments.CartFragment;
import com.example.foodorderapp.fragments.HomeFragment;
import com.example.foodorderapp.fragments.LoginFragment;
import com.example.foodorderapp.fragments.NotificationFragment;
import com.example.foodorderapp.helpers.GlobalUser;
import com.example.foodorderapp.helpers.MainFacade;
import com.example.foodorderapp.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;

    private GlobalUser globalUser = GlobalUser.getGlobalUser();

    private MainFacade facade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContent, new HomeFragment());
        fragmentTransaction.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                displayView(item);

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
        facade = new MainFacade(this.findViewById(android.R.id.content));
        facade.showLogin();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void displayView(MenuItem item){
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
//                item.setChecked(true);
                break;
            case R.id.navigation_dashboard:
                fragment = new CartFragment();
//                item.setChecked(true);
                break;
            case R.id.navigation_notifications:
                fragment = new NotificationFragment();
//                item.setChecked(true);
                break;
            case R.id.navigation_login:
                fragment = new LoginFragment();
//                item.setChecked(true);
                break;
            case R.id.navigation_logout:
                globalUser.setGlobalUser(new User());
                facade.updateUserInfo("User name", "Email@gmail.com");
                facade.showLogin();
                break;
        }

        if(fragment != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContent, fragment);
            fragmentTransaction.commit();
        }
    }
}