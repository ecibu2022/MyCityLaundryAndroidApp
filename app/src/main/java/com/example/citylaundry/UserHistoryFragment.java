package com.example.citylaundry;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryFragment extends Fragment {
    private RecyclerView user_history;
    private DatabaseReference databaseReference;
    private List<UserServicesModal> myItemsList;
    private UserHistoryAdapter adapter;

    public UserHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_history, container, false);
        user_history=view.findViewById(R.id.user_history);
        user_history.setHasFixedSize(true);
        // Setting its Layout
        user_history.setLayoutManager(new LinearLayoutManager(getContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("Loading Services");
        builder.setTitle("Loading history of services");
        builder.setMessage("Please wait......");
        AlertDialog dialog = builder.create();
        dialog.show();

        myItemsList = new ArrayList<>();
        adapter = new UserHistoryAdapter(getContext(), myItemsList);
        user_history.setAdapter(adapter);

        // Retrieving all services for specific user logged in
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("users").child(currentUserID);
        databaseReference = FirebaseDatabase.getInstance().getReference("services");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       myItemsList.clear();
                       for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                           UserServicesModal myItems = itemSnapshot.getValue(UserServicesModal.class);
                           myItems.setKey(itemSnapshot.getKey());
                           myItemsList.add(myItems);
                       }
                       adapter.notifyDataSetChanged();
                       dialog.dismiss();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Toast.makeText(getContext(), "No history found", Toast.LENGTH_SHORT).show();
                   }
               });
                // Retrieving all services
                databaseReference = FirebaseDatabase.getInstance().getReference("services");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myItemsList.clear();
                        for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                            // Make sure to use the actual field names in your Firebase data
                            String id = itemSnapshot.child("id").getValue(String.class);
                            String userID = itemSnapshot.child("userID").getValue(String.class);
                            String item = itemSnapshot.child("item").getValue(String.class);
                            String washing = itemSnapshot.child("washing").getValue(String.class);
                            String cleaning = itemSnapshot.child("cleaning").getValue(String.class);
                            String ironing = itemSnapshot.child("ironing").getValue(String.class);
                            String city = itemSnapshot.child("city").getValue(String.class);
                            String state = itemSnapshot.child("state").getValue(String.class);
                            String apartment = itemSnapshot.child("apartment").getValue(String.class);
                            String info = itemSnapshot.child("info").getValue(String.class);
                            String currentDate = itemSnapshot.child("currentDate").getValue(String.class);
                            String status = itemSnapshot.child("status").getValue(String.class);
                            String price = itemSnapshot.child("price").getValue(String.class);

                            UserServicesModal myItems = new UserServicesModal(id, userID, item, washing, cleaning, ironing, city, state, apartment, info, currentDate, status, price);
                            // Assuming 'key' is a field in your Firebase data
                            myItems.setKey(itemSnapshot.getKey());
                            myItemsList.add(myItems);
                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        return view;
    }
}