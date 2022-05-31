package com.example.foodorderapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.activities.MainActivity;
import com.example.foodorderapp.helpers.GlobalUser;
import com.example.foodorderapp.helpers.MainFacade;
import com.example.foodorderapp.interfaces.ApiService;

import com.example.foodorderapp.models.Login;
import com.example.foodorderapp.models.User;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    NavigationView navigationView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnLogin, btnRegister;
    private EditText etEmail, etPassword;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegisterPage);
        etEmail = view.findViewById(R.id.email);
        etPassword = view.findViewById(R.id.password);
        btnLogin.setOnClickListener(v ->{
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            Login loginData = new Login(email, password);
            ApiService.apiService.login(loginData).enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("log", response.body().toString());
                    GlobalUser user = GlobalUser.getGlobalUser();
                    user.setGlobalUser(response.body());
                    Log.d("log", "name: "+user.getName());
                    Log.d("log", "email: "+user.getEmail());

                    MainFacade facade = new MainFacade(getActivity().getCurrentFocus());
                    facade.updateUserInfo(user.getName(), user.getEmail());

                    SharedPreferences settings = getActivity().getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("email", user.getEmail());
                    editor.apply();

                    Fragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flContent, homeFragment);
                    fragmentTransaction.commit();
                    facade.showLogout();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getActivity(), "Login fail", Toast.LENGTH_LONG).show();
                }
            });
        });
        btnRegister.setOnClickListener(v ->{
            Fragment registerFragment = new RegisterFragment();
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContent, registerFragment);
            fragmentTransaction.commit();
        });
    }
}