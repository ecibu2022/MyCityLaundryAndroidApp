package com.example.citylaundry;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserServicesFragment extends Fragment {
    private EditText item, city, state, apartment, info;
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
        city=view.findViewById(R.id.city);
        state=view.findViewById(R.id.state);
        apartment=view.findViewById(R.id.apartment);
        info=view.findViewById(R.id.info);
        save=view.findViewById(R.id.save);
        databaseReference= FirebaseDatabase.getInstance().getReference("services");

        progressDialog=new ProgressDialog(getContext());

        washing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Washing = "Washing";
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
                String City=city.getText().toString().trim();
                String State=state.getText().toString().trim();
                String Apartment=apartment.getText().toString().trim();
                String Info=info.getText().toString().trim();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID= user.getUid();
                String id=databaseReference.push().getKey();
                Date currentDate=new Date();
                UserServicesModal servicesModal=new UserServicesModal(id, userID, Item, Washing, Cleaning, Ironing, City, State, Apartment, Info, currentDate.toString(), "Pending", "0");
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
                            city.setText(null);
                            state.setText(null);
                            apartment.setText(null);
                            info.setText(null);
                            sendNotification("New Service Requested");
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

    //    Send Notification Method to a specific user
    private void sendNotification(String notificationTitle) {
        DatabaseReference adminTokenRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query adminQuery = adminTokenRef.orderByChild("role").equalTo("admin");
        adminQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adminSnapshot : dataSnapshot.getChildren()) {
                    String adminToken = adminSnapshot.child("deviceToken").getValue(String.class);
                    String name = adminSnapshot.child("role").getValue(String.class);
                    Log.d("Admin Token: ", adminToken);
                    Log.d("Role", name);
                    if (adminToken != null) {
                        // Send the notification using FCM
                        sendFCMNotificationToAdmin(adminToken, notificationTitle);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any database error that occurred while fetching the data
            }
        });
    }

    private void sendFCMNotificationToAdmin(String adminToken, String notificationTitle) {
        // Set the FCM server key from Firebase Console
        String serverKey = "AAAAfzFstEo:APA91bHMc4WffmzQv707_vGfyMB1cyq99Q7QzQC0Mm18tP0Z13z2Z9QM1Zk16R4uzqk-i5KFZUqfcPgIdwAk6ZfL1Op4YwREB2Dc_ByQHnDpkCpTfGoIz_LUYBKV0MLWXPjdb5x4m6xJ";

        // Create the FCM message data payload (customize as needed)
        Map<String, String> data = new HashMap<>();
        data.put("title", "New Service Requested");
        data.put("body", notificationTitle);

        // Create the FCM message body
        Map<String, Object> message = new HashMap<>();
        message.put("to", adminToken);
        message.put("data", data);

        // Send the FCM message using OkHttp
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(message));
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=" + serverKey)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("FCM", "Failed to send notification to admin", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("FCM", "Notification sent to admin");
                } else {
                    Log.e("FCM", "Failed to send notification to admin");
                }
                response.close();
            }
        });
    }

}