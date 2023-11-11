package com.example.citylaundry;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserServicesFragment extends Fragment {
    private EditText item;
    private CheckBox washing, cleaning, ironing;
    private Button save;
    private String Washing, Cleaning, Ironing;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    public UserServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_services, container, false);

        item=view.findViewById(R.id.item);
        washing=view.findViewById(R.id.washing);
        cleaning=view.findViewById(R.id.cleaning);
        ironing=view.findViewById(R.id.ironing);
        save=view.findViewById(R.id.save);
        databaseReference= FirebaseDatabase.getInstance().getReference("services");

        progressDialog=new ProgressDialog(getContext());

        washing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Washing = "Ironing";
                } else {
                    Washing=null;
                }
            }
        });

        cleaning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Cleaning ="Cleaning";
                } else {
                    Cleaning=null;
                }
            }
        });

        ironing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Ironing = "Ironing";
                } else {
                    Ironing=null;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Saving your service ......");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String Item=item.getText().toString().trim();
                String id=databaseReference.push().getKey();
                UserServicesModal servicesModal=new UserServicesModal(id, Item, Washing, Cleaning, Ironing);
                databaseReference.child(id).setValue(servicesModal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Service saved successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            item.setText(null);
                            washing.setChecked(false);
                            cleaning.setChecked(false);
                            ironing.setChecked(false);
                        }else{
                            Toast.makeText(getContext(), "Failed to save service", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error try again", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }

}