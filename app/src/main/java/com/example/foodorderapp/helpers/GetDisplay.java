package com.example.foodorderapp.helpers;

import android.view.View;
import android.widget.TextView;

import com.example.foodorderapp.R;

public class GetDisplay {
    private View view;
    public GetDisplay(View view){
        this.view = view;
    }

    public TextView getNameDisplay(){
        return (TextView)view.getRootView().findViewById(R.id.displayName);
    }

    public TextView getEmailDisplay(){
        return (TextView)view.getRootView().findViewById(R.id.displayEmail);
    }


}
