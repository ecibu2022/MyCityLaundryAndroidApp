package com.example.citylaundry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private ArrayList<UserServicesModal> services;
    private Context context;
    String key = "";
    private DatabaseReference databaseReference;
    private EditText txt;

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
        holder.state.setText(services.get(position).getState());

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
                AlertDialog.Builder alertName = new AlertDialog.Builder(context);
                final EditText editTextName1 = new EditText(context);
                // add line after initializing editTextName1
                editTextName1.setHint("Enter price of the service");
                alertName.setTitle("***Confirm Service Requested***");
                alertName.setView(editTextName1);
                LinearLayout layoutName = new LinearLayout(context);
                layoutName.setOrientation(LinearLayout.VERTICAL);
                layoutName.addView(editTextName1);
                alertName.setView(layoutName);

                alertName.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        txt = editTextName1;
                        collectInput();
                    }
                });

                alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alertName.show();

            }
        });

    }

    public void collectInput(){
        // convert edit text to string
        String getInput = txt.getText().toString();

        // ensure that user input bar is not empty
        if (getInput.trim() == null || getInput.trim().equals("")){
            Toast.makeText(context, "Please enter the price of the service", Toast.LENGTH_LONG).show();
        } else {
            // Update Status and price of service
            databaseReference = FirebaseDatabase.getInstance().getReference("services");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot serviceSnapshot : snapshot.getChildren()) {
                        String id = serviceSnapshot.child("id").getValue(String.class);

                        if (id != null) {
                            // Update price and status for the specific service
                            databaseReference.child(id).child("price").setValue(getInput);
                            databaseReference.child(id).child("status").setValue("Assigned");
                            Toast.makeText(context, "Service Approved Successfully", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Failed to approve service", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return services.size();
    }
    static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, washing, cleaning, ironing, state;
        private Button approve;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.item);
            washing=itemView.findViewById(R.id.washing);
            cleaning=itemView.findViewById(R.id.cleaning);
            ironing=itemView.findViewById(R.id.ironing);
            state=itemView.findViewById(R.id.state);
            approve=itemView.findViewById(R.id.approve);

        }
    }
}
