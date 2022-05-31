package com.example.foodorderapp.helpers;

import com.example.foodorderapp.models.User;

// Singleton pattern

public class GlobalUser extends User {
    private static final GlobalUser globalUser = new GlobalUser();

    private GlobalUser(){}

    public static GlobalUser getGlobalUser(){
        return globalUser;
    }

    public void setGlobalUser(User user){
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setToken(user.getToken());
    }

}
