package com.example.maintainmore.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maintainmore.LoginActivity;
import com.example.maintainmore.R;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProfileFragment extends Fragment {

    private static final int IMAGE_REQUEST_ID = 1;
    private static final String TAG = "ProfileFragmentInfo";

    private Uri uri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    Button buttonLogout, buttonDeleteAccount, buttonSave;
    TextView textViewEmail, displayName, displayEmail;
    ImageButton buttonChangePicture;
    ImageView profilePicture;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db ;

    DocumentReference documentReference;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.i(TAG,"ProfileFragment");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        textViewEmail = view.findViewById(R.id.textViewEmailID);
        profilePicture = view.findViewById(R.id.profilePicture);

        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonDeleteAccount = view.findViewById(R.id.buttonDeleteAccount);
        buttonChangePicture = view.findViewById(R.id.buttonChangePicture);
        buttonSave = view.findViewById(R.id.buttonSave);

        displayName = view.findViewById(R.id.displayName);
        displayEmail = view.findViewById(R.id.displayEmail);







        if (firebaseUser!=null) {
            buttonLogout.setOnClickListener(view1 -> {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                requireActivity().finish();
            });

            buttonDeleteAccount.setOnClickListener(view1 -> deleteAccount());
            buttonChangePicture.setOnClickListener(view1 -> changePicture());
            buttonSave.setOnClickListener(view1 -> saveToDatabase());


            String mail = Objects.requireNonNull(firebaseUser).getEmail();
            String userID = Objects.requireNonNull(firebaseUser).getUid();



            documentReference = db.collection("Users").document(userID);

            documentReference.addSnapshotListener((value, error) -> {
                if (error != null) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
                if (value != null && value.exists()){
                    displayName.setText(value.getString("name"));
                    displayEmail.setText(value.getString("email"));
                    Glide.with(requireActivity()).load(value.getString("imageUrl"))
                            .placeholder(R.drawable.ic_person).into(profilePicture);
                }
            });

            textViewEmail.setText(mail);

        }



        return view;
    }

    private void saveToDatabase() {

        String userID = Objects.requireNonNull(firebaseUser).getUid();

        if (uri != null){
            storageReference = storageReference.child("Profile Pictures/" +userID);
            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {

                storageReference.getDownloadUrl().addOnSuccessListener(uri -> setImageURL(String.valueOf(uri)));

                Toast.makeText(getActivity(), "Picture Saved", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());



        }
    }

    private void setImageURL(String uri) {
        String userID = Objects.requireNonNull(firebaseUser).getUid();

        db.collection("Users").document(userID).update(
                "imageUrl", uri
        ).addOnSuccessListener(unused -> Toast.makeText(getActivity(), "link created", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to create link" + e, Toast.LENGTH_SHORT).show());

        Log.i(TAG, "Saved link: " + uri);
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setIcon(R.drawable.ic_delete_forever);
        builder.setTitle(R.string.delete_account);
        builder.setMessage(R.string.delete_account_massage);
        builder.setPositiveButton("Yes", (dialogInterface, i) ->
                firebaseUser.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(requireActivity(),SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("Account Deleted Successful");
                sweetAlertDialog.setConfirmClickListener(sweetAlertDialog1->{
                startActivity(new Intent(requireActivity(),LoginActivity.class));
                requireActivity().finishAffinity();
                sweetAlertDialog1.dismissWithAnimation();
                }).setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
            }
            else {
                new SweetAlertDialog(requireActivity(),SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(Objects.requireNonNull(task.getException()).getMessage()).show();
            }
        }));
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }
    
    private void changePicture(){
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                profilePicture.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}