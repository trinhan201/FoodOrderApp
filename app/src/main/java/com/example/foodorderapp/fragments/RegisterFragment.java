package com.example.foodorderapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.helpers.GlobalUser;
import com.example.foodorderapp.interfaces.ApiService;
import com.example.foodorderapp.models.Register;
import com.example.foodorderapp.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnRegister;
    private EditText emailRegister, nameRegister, phoneRegister, addressRegister, passwordRegister, rePasswordRegister;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailRegister = view.findViewById(R.id.emailRegister);
        nameRegister = view.findViewById(R.id.nameRegister);
        phoneRegister = view.findViewById(R.id.phoneRegister);
        addressRegister = view.findViewById(R.id.addressRegiter);
        passwordRegister = view.findViewById(R.id.passwordRegister);
        rePasswordRegister = view.findViewById(R.id.rePasswordRegister);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v->{
            if (passwordRegister.getText().toString().equals(rePasswordRegister.getText().toString())){
                Register register = new Register();
                register.setEmail(emailRegister.getText().toString());
                register.setName(nameRegister.getText().toString());
                register.setPhone(phoneRegister.getText().toString());
                register.setAddress(addressRegister.getText().toString());
                register.setPassword(passwordRegister.getText().toString());

                ApiService.apiService.register(register).enqueue(new Callback<String>(){

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getActivity(), "Register success", Toast.LENGTH_LONG).show();
                        Fragment loginFragment = new LoginFragment();
                        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, loginFragment);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(), "Register fail", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                Toast.makeText(getActivity(), "Mật khẩu bạn nhập không trùng khớp", Toast.LENGTH_LONG).show();
            }
        });

    }
}