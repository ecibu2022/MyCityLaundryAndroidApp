package com.example.citylaundry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

public class NotifyAdmin extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_admin);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Notifications");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NotifyAdmin.this, Admin.class));
        finish();
        super.onBackPressed();
    }

}