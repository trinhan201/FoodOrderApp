package com.example.foodorderapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.activities.FoodDetailActivity;
import com.example.foodorderapp.models.FoodModel;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    private List<FoodModel> lstFood;
    private Context mContext;

    public FoodAdapter(List<FoodModel> lstFood, Context context) {
        this.lstFood = lstFood;
        this.mContext = context;
    }


    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        FoodModel foodModel = lstFood.get(position);
        if(foodModel==null)
            return;
        if(foodModel.getName().length() > 10){
            holder.tvName.setText(foodModel.getName().substring(0,9).concat("..."));
        }else {
            holder.tvName.setText(foodModel.getName());
        }
        holder.tvPrice.setText(String.valueOf(foodModel.getPrice()));
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(mContext, FoodDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", foodModel);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(lstFood!=null)
            return lstFood.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView layout_item;
        TextView tvName, tvPrice;
        Button btnAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvPrice = itemView.findViewById((R.id.price));
            btnAdd = itemView.findViewById(R.id.btnAddToCart);
            layout_item = itemView.findViewById(R.id.layout_item);

        }
    }
}
