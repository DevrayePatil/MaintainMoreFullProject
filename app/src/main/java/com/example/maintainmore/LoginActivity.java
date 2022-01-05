package com.example.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button buttonSkip, buttonSignup, buttonShowMap;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        toolbar =  findViewById(R.id.toolbar);
        buttonSkip = findViewById(R.id.buttonSkip);
        buttonSignup = findViewById(R.id.buttonSignup);
        buttonShowMap = findViewById(R.id.buttonShowMap);

        setSupportActionBar(toolbar);

        buttonSkip.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HomeActivity.class)));
        buttonShowMap.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)));
        buttonSignup.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SignupActivity.class)));


    }
}