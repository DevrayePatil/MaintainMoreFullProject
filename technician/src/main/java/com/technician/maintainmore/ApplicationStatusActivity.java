package com.technician.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class ApplicationStatusActivity extends AppCompatActivity {


    String technicianID;
    FirebaseStorage firebaseStorage;
    FirebaseUser technician;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db ;


    Toolbar toolbar;


    protected void onStart() {

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        technician = firebaseAuth.getCurrentUser();
        technicianID = Objects.requireNonNull(technician).getUid();

        if (technician !=null) {

            technicianID = Objects.requireNonNull(technician).getUid();

            db.collection("Technicians").document(technicianID).
                    addSnapshotListener((value, error) ->{

                        if (value != null && Objects.requireNonNull(value.getString("approvalStatus")).equals("Approved")) {
                            startActivity(new Intent(this, MainActivity.class));
                        }
                    });

        }

        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_status);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}