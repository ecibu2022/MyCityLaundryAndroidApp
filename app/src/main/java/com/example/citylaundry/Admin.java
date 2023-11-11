package com.example.citylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Admin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

//        Hiding Status bar
        hideStatusBar();

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        drawerLayout=findViewById(R.id.drawer_layout);

        //        Makes Home active when back button is clicked either in settings or profile it goes back to home
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    AdminHomeFragment homeFragment=new AdminHomeFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.home) {
            fragment = new AdminHomeFragment();
        }else if (id == R.id.person) {
            fragment = new PersonFragment();
        }else if (id == R.id.settings) {
            fragment = new SettingsFragment();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();
        }

        return true;
    }

    private void hideStatusBar() {
        // Set the activity to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the status bar on Android versions >= 16 (API level 16)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibilityFlags = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(systemUiVisibilityFlags);
        }
    }

}