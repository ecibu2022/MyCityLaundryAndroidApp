package com.example.citylaundry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;;
import java.util.List;

public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.MyViewHolder> {
    private ArrayList<UserServicesModal> services;
    private Context context;

    public UserHistoryAdapter(Context context, List<UserServicesModal> items) {
        this.services = (ArrayList<UserServicesModal>) items;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Inflating the design of the pharmacy layout
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHistoryAdapter.MyViewHolder holder, int position) {
        holder.price.setText(services.get(position).getPrice());
        holder.date.setText(services.get(position).getCurrentDate());
        holder.status.setText(services.get(position).getStatus());
        // Check if washing service is present
        if (services.get(position).getWashing() != null && !services.get(position).getWashing().isEmpty()) {
            holder.washing.setText(services.get(position).getWashing());
            holder.washing.setVisibility(View.VISIBLE);  // Show the TextView
        } else {
            holder.washing.setVisibility(View.GONE);  // Hide the TextView
            holder.washingLayout.setVisibility(View.GONE);
        }

        // Check if cleaning service is present
        if (services.get(position).getCleaning() != null && !services.get(position).getCleaning().isEmpty()) {
            holder.cleaning.setText(services.get(position).getCleaning());
            holder.cleaning.setVisibility(View.VISIBLE);  // Show the TextView
        } else {
            holder.cleaning.setVisibility(View.GONE);  // Hide the TextView
            holder.cleaningLayout.setVisibility(View.GONE);
        }

        // Check if ironing service is present
        if (services.get(position).getIroning() != null && !services.get(position).getIroning().isEmpty()) {
            holder.ironing.setText(services.get(position).getIroning());
            holder.ironing.setVisibility(View.VISIBLE);  // Show the TextView
        } else {
            holder.ironing.setVisibility(View.GONE);  // Hide the TextView
            holder.ironingLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return services.size();
    }
    static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView price, date, status, washing, cleaning, ironing, washingLayout, cleaningLayout, ironingLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            washing=itemView.findViewById(R.id.washing);
            cleaning=itemView.findViewById(R.id.cleaning);
            ironing=itemView.findViewById(R.id.ironing);
            washingLayout=itemView.findViewById(R.id.washingLayout);
            cleaningLayout=itemView.findViewById(R.id.cleaningLayout);
            ironingLayout=itemView.findViewById(R.id.ironingLayout);

        }
    }
}
