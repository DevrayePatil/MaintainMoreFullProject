package com.example.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import com.example.maintainmore.Fragments.HomeFragment;


public class DetailedViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);


        setContentView(R.layout.activity_detailed_view);


        Intent intent = getIntent();

        String name = intent.getStringExtra("cardView");
    }


}