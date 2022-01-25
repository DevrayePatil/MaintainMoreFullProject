package com.example.maintainmore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maintainmore.ServiceDetailsActivity;
import com.example.maintainmore.R;


public class BookingFragment extends Fragment {

    Button buttonBooking;

    public BookingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);



        buttonBooking = view.findViewById(R.id.buttonBooking);

        buttonBooking.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), ServiceDetailsActivity.class)));

        return view;
    }
}