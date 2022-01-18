package com.example.maintainmore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.maintainmore.LoginActivity;
import com.example.maintainmore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProfileFragment extends Fragment {

    Button buttonLogout, buttonDeleteAccount;
    TextView textViewEmail;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonDeleteAccount = view.findViewById(R.id.buttonDeleteAccount);
        textViewEmail = view.findViewById(R.id.textViewEmail);



        if (firebaseUser!=null) {
            buttonLogout.setOnClickListener(view1 -> {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                requireActivity().finish();
            });

            buttonDeleteAccount.setOnClickListener(view1 -> deleteAccount());


            String mail = Objects.requireNonNull(firebaseUser).getEmail();
            textViewEmail.setText(mail);
        }



        return view;
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setIcon(R.drawable.ic_delete_forever);
        builder.setTitle(R.string.delete_account);
        builder.setMessage(R.string.delete_account_massage);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> firebaseUser.delete().addOnCompleteListener(task -> {
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
}