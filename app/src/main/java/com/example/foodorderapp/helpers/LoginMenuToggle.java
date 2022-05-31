package com.example.foodorderapp.helpers;

import android.view.Menu;
import android.view.View;

import com.example.foodorderapp.R;
import com.google.android.material.navigation.NavigationView;

public class LoginMenuToggle {

    private View view;

    public LoginMenuToggle(View view){
        this.view = view;
    }

    public void hideLogout() {
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.navigation_login).setVisible(true);
        nav_Menu.findItem(R.id.navigation_logout).setVisible(false);
    }
    public void showLogout() {
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.navigation_login).setVisible(false);
        nav_Menu.findItem(R.id.navigation_logout).setVisible(true);
    }
}
