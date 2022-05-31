package com.example.foodorderapp.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapters.CartListAdapter;
import com.example.foodorderapp.helpers.GlobalUser;
import com.example.foodorderapp.helpers.ManagementCart;
import com.example.foodorderapp.interfaces.ApiService;
import com.example.foodorderapp.interfaces.ChangeNumberItemsListener;
import com.example.foodorderapp.models.FoodModel;
import com.example.foodorderapp.models.Order;
import com.example.foodorderapp.models.OrderItem;
import com.example.foodorderapp.models.OrderResponse;
import com.example.foodorderapp.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    Button btnCheckOut;
//    private double tax;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        managementCart = new ManagementCart(getActivity());

//        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
//        taxTxt = view.findViewById(R.id.taxTxt);
//        deliveryTxt = view.findViewById(R.id.deliveryTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        emptyTxt = view.findViewById(R.id.emptyTxt);
        btnCheckOut = view.findViewById(R.id.btnCheckOut);
        scrollView = view.findViewById(R.id.scrollView3);
        recyclerViewList=view.findViewById(R.id.cartView);
        initList();
        CalculateCart();
    }

    private void initList() {

        btnCheckOut.setOnClickListener(v ->{
            GlobalUser user = GlobalUser.getGlobalUser();

            if (user.getToken() == null){
                Toast.makeText(this.getActivity(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    // TODO Chỗ này có thê dùng adapter pattern

                    ArrayList<FoodModel> cartItems = managementCart.getListCart();
                    ArrayList<OrderItem> orderItems = new ArrayList<>();
                    cartItems.forEach(item->{
                        orderItems.add(new OrderItem(item.get_id(), item.getNumberInCart()));
                    });

                    Order order = new Order();
                    order.setOrderItems(orderItems);

                    ApiService.apiService.order(user.getToken(), order).enqueue(new Callback<OrderResponse>(){

                        @Override
                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                            Toast.makeText(getActivity(), "Order success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<OrderResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Login fail", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), getActivity(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void CalculateCart() {
//        double percentTax = 0.02;
//        double delivery = 10000;

//        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100;
        double total = Math.round((managementCart.getTotalFee()) * 100) / 100;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100;

//        totalFeeTxt.setText(itemTotal + "VND");
//        taxTxt.setText(tax + "VND");
//        deliveryTxt.setText(delivery + "VND");
        totalTxt.setText(total + "VND");
    }
}