package com.example.citylaundry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private ArrayList<UserServicesModal> services;
    private Context context;
    String key = "";

    public ServicesAdapter(Context context, List<UserServicesModal> items) {
        this.services = (ArrayList<UserServicesModal>) items;
        this.context = context;
    }

    @NonNull
    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Inflating the design of the pharmacy layout
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.services_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(services.get(position).getItem());

        // Check if washing service is present
        if (services.get(position).getWashing() != null && !services.get(position).getWashing().isEmpty()) {
            holder.washing.setText(services.get(position).getWashing());
            holder.washing.setVisibility(View.VISIBLE);  // Show the TextView
        } else {
            holder.washing.setVisibility(View.GONE);  // Hide the TextView
        }

        // Check if cleaning service is present
        if (services.get(position).getCleaning() != null && !services.get(position).getCleaning().isEmpty()) {
            holder.cleaning.setText(services.get(position).getCleaning());
            holder.cleaning.setVisibility(View.VISIBLE);  // Show the TextView
        } else {
            holder.cleaning.setVisibility(View.GONE);  // Hide the TextView
        }

        // Check if ironing service is present
        if (services.get(position).getIroning() != null && !services.get(position).getIroning().isEmpty()) {
            holder.ironing.setText(services.get(position).getIroning());
            holder.ironing.setVisibility(View.VISIBLE);  // Show the TextView
        } else {
            holder.ironing.setVisibility(View.GONE);  // Hide the TextView
        }

//        Approve Button
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Service Approved", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return services.size();
    }
    static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView washing;
        TextView cleaning;
        TextView ironing;
        private Button approve;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.item);
            washing=itemView.findViewById(R.id.washing);
            cleaning=itemView.findViewById(R.id.cleaning);
            ironing=itemView.findViewById(R.id.ironing);

            approve=itemView.findViewById(R.id.approve);

        }
    }
}
