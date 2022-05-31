package com.example.foodorderapp.helpers;

import android.view.View;
import android.widget.TextView;

public class SetDisplayTextView {
    private TextView tv;

    public SetDisplayTextView(TextView tv){
        this.tv = tv;
    }

    public void setValue(String value){
        tv.setText(value);
    }
}
