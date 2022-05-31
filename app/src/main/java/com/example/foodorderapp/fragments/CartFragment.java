package com.example.foodorderapp.fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.foodorderapp.observer.CustomerEmailObserver;
import com.example.foodorderapp.observer.EmailData;
import com.example.foodorderapp.observer.MailService;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CustomerEmailObserver {
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
        MailService mailService = new MailService();
        mailService.registerObserver(this);
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
                            mailService.Notify();
                            Toast.makeText(getActivity(), "Order success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<OrderResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Order fail", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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

    @Override
    public void sendEmail() {
        final String username = "doccocaubai012@gmail.com"; // username email nguoi gui
        final String password = "sucmanhhamaarm"; // password email nguoi gui

        EmailData emailData = new EmailData();
        SharedPreferences settings = this.getActivity().getSharedPreferences("PREFS",0);
        String email = settings.getString("email","");
        emailData.setAddress(email);
        emailData.setSubject("Thông báo xác nhận đơn hàng");
        emailData.setMessage("Cám ơn quý khách đã đặt mua thức ăn");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
        );
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            InternetAddress toAddress = InternetAddress.parse(emailData.getAddress())[0];
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(emailData.getSubject());
            message.setText(emailData.getMessage());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}