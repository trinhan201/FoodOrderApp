package com.example.foodorderapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.helpers.ManagementCart;
import com.example.foodorderapp.models.FoodModel;

public class FoodDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, descriptionTxt, numberOrderTxt,priceTxt;
    private ImageView plusBtn, minusBtn, imgFood;
    private FoodModel object;
    int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        managementCart = new ManagementCart(this);
        initView();
        getBundle();

    }

    private void getBundle() {
        object = (FoodModel) getIntent().getSerializableExtra("object");

        String img = String.valueOf(object.getImages());
        img = img.substring(1,img.length() - 1);
        Glide.with(this).load(img).into(imgFood);

        titleTxt.setText(object.getName());
        descriptionTxt.setText(object.getDescription());
        priceTxt.setText(String.valueOf(object.getPrice()));
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);
            }
        });
    }

    private void initView() {
        imgFood = findViewById(R.id.imgFood);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.tvName);
        descriptionTxt = findViewById(R.id.tvDesc);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        priceTxt = findViewById(R.id.tvPrice);
    }
}