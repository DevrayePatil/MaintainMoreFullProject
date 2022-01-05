package com.example.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.widget.LinearLayout;

import com.example.maintainmore.Fragments.BookingFragment;
import com.example.maintainmore.Fragments.HomeFragment;
import com.example.maintainmore.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;



    BottomNavigationView bottomNavigationView;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_home);

        setSupportActionBar(toolbar);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        linearLayout = findViewById(R.id.fragmentContainer);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelected);


        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
        fragmentTransaction.commit();
    }


    public BottomNavigationView.OnNavigationItemSelectedListener itemSelected = item -> {
        if (item.getItemId() == R.id.home){

            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
            fragmentTransaction.commit();

        }
        else if(item.getItemId() == R.id.booking){
            BookingFragment bookingFragment = new BookingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, bookingFragment);
            fragmentTransaction.commit();


        }else if(item.getItemId() == R.id.profile){

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, profileFragment);
            fragmentTransaction.commit();
        }

        return true;
    };
}