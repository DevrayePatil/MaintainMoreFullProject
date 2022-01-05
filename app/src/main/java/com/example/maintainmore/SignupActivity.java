package com.example.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class SignupActivity extends AppCompatActivity {

    Button buttonLogin;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        buttonLogin = findViewById(R.id.buttonLogin);

        setSupportActionBar(toolbar);

        buttonLogin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

    }
}