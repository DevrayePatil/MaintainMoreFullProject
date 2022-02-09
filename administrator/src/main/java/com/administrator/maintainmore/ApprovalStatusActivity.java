package com.administrator.maintainmore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ApprovalStatusActivity extends AppCompatActivity {

    private static final String TAG = "ApprovalStatusActivityInfo";

    String technicianID, technicianName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_status);

        GetDataFromPreviousActivity();
    }

    @SuppressLint("LongLogTag")
    private void GetDataFromPreviousActivity(){
        Intent intent = getIntent();

        technicianID = intent.getStringExtra("technicianID");
        technicianName = intent.getStringExtra("technicianName");

        Log.i(TAG,"ID: " + technicianID);
        Log.i(TAG,"Name: " + technicianName);
    }
}