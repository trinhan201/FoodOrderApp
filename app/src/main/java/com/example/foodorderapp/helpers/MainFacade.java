package com.example.foodorderapp.helpers;

import android.view.View;
import android.widget.TextView;

public class MainFacade {

    private View view;

    public MainFacade(View view){
        this.view = view;
    }

    public void updateUserInfo(String name, String email){
        GetDisplay getDisplay = new GetDisplay(view);
        TextView displayName = getDisplay.getNameDisplay();
        TextView displayEmail = getDisplay.getEmailDisplay();
        SetDisplayTextView tvName = new SetDisplayTextView(displayName);
        SetDisplayTextView tvEmail = new SetDisplayTextView(displayEmail);
        tvName.setValue(name);
        tvEmail.setValue(email);
    }

    public void showLogin(){
        LoginMenuToggle toggle = new LoginMenuToggle(view);
        toggle.hideLogout();
    }
    public void showLogout(){
        LoginMenuToggle toggle = new LoginMenuToggle(view);
        toggle.showLogout();
    }
}
