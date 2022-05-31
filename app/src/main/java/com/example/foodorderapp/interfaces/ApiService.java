package com.example.foodorderapp.interfaces;

import com.example.foodorderapp.models.FoodModel;
import com.example.foodorderapp.models.Login;
import com.example.foodorderapp.models.Order;
import com.example.foodorderapp.models.OrderResponse;
import com.example.foodorderapp.models.Register;
import com.example.foodorderapp.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://android-coffee-0902.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/v1/products")
    Call<List<FoodModel>> callListFood();

    @POST("api/v1/customers/login")
    Call<User> login(@Body Login login);

    @POST("api/v1/customers/register")
    Call<String> register(@Body Register register);

    @POST("api/v1/orders/customer")
    Call<OrderResponse> order(@Header("Authorization") String token, @Body Order order);
}
