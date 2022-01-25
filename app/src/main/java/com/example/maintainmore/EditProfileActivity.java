package com.example.maintainmore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_ID = 1;
    private static final String TAG = "EditProfileActivityInfo";
    String checkedName;

    private Uri uri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db ;

    DocumentReference documentReference;


    TextView displayName, displayEmail;
    ImageView profilePicture;
    EditText fullName, email, phoneNumber, dateOfBirth;

    Button buttonCancel, buttonSave, buttonSavePicture;
    ImageButton buttonChangePicture;

    RadioGroup radioGroup;
    RadioButton radioButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);

        setContentView(R.layout.activity_edit_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        displayName = findViewById(R.id.displayName);
        displayEmail = findViewById(R.id.displayEmail);

        profilePicture = findViewById(R.id.profilePicture);

        fullName = findViewById(R.id.textInputLayout_FullName);
        email = findViewById(R.id.textInputLayout_Email);
        phoneNumber = findViewById(R.id.textInputLayout_Phone);
        dateOfBirth = findViewById(R.id.textInputLayout_DOB);

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSave = findViewById(R.id.buttonSave);
        buttonChangePicture = findViewById(R.id.buttonChangePicture);
        buttonSavePicture = findViewById(R.id.buttonSavePicture);

        radioGroup = findViewById(R.id.radioGroup);


        buttonCancel.setOnClickListener(view -> finish());
        buttonSave.setOnClickListener(view -> UpdatePersonalInformation());
        buttonChangePicture.setOnClickListener(view -> ChangePicture());
        buttonSavePicture.setOnClickListener(view -> SavePicture());

        if (firebaseUser!=null) {
            LoginUserInfo();
        }

        PersonalInformation();


    }

    private void LoginUserInfo() {
        String userID = Objects.requireNonNull(firebaseUser).getUid();



        documentReference = db.collection("Users").document(userID);

        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){
                displayName.setText(value.getString("name"));
                displayEmail.setText(value.getString("email"));
                Glide.with(getApplicationContext()).load(value.getString("imageUrl"))
                        .placeholder(R.drawable.ic_person_png).into(profilePicture);
            }
        });
    }

    private void UpdatePersonalInformation() {

        String userID = Objects.requireNonNull(firebaseUser).getUid();

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioButtonID);
        checkedName = radioButton.getText().toString();

        String FullName = fullName.getText().toString();
//        String EmailID = email.getText().toString();
        String PhoneNumber = phoneNumber.getText().toString();
        String DOB = dateOfBirth.getText().toString();

        db.collection("Users").document(userID).update(
                "name", FullName,
                "gender", checkedName,
                "phoneNumber", PhoneNumber,
                "dob", DOB


        ).addOnSuccessListener(unused ->
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getApplicationContext(), "Failed to create link" + e,
                                Toast.LENGTH_SHORT).show());

    }
    private void SavePicture() {

        String userID = Objects.requireNonNull(firebaseUser).getUid();

        if (uri != null){
            storageReference = storageReference.child("Profile Pictures/" +userID);
            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {

                storageReference.getDownloadUrl().addOnSuccessListener(uri -> setImageURL(String.valueOf(uri)));

                Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());



        }
    }

    private void setImageURL(String uri) {
        String userID = Objects.requireNonNull(firebaseUser).getUid();

        db.collection("Users").document(userID).update(
                "imageUrl", uri
        ).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "link created", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to create link" + e, Toast.LENGTH_SHORT).show());

        Log.i(TAG, "Saved link: " + uri);
    }
    private void PersonalInformation() {

        RadioButton radioButtonMale, radioButtonFemale;

        radioButtonMale = findViewById(R.id.radio_button_1);
        radioButtonFemale = findViewById(R.id.radio_button_2);

        String userID = Objects.requireNonNull(firebaseUser).getUid();

        documentReference = db.collection("Users").document(userID);

        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){
                fullName.setText(value.getString("name"));
                email.setText(value.getString("email"));
                phoneNumber.setText(value.getString("phoneNumber"));
                dateOfBirth.setText(value.getString("dob"));

                String genderValue = value.getString("gender");

                if (Objects.equals(genderValue, "Male")){
                    radioButtonMale.setChecked(true);
                }else {
                    radioButtonFemale.setChecked(true);
                }
            }
        });

    }

    private void ChangePicture(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Choose Image"),IMAGE_REQUEST_ID);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null &
                (data != null ? data.getData() : null) != null){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                profilePicture.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}