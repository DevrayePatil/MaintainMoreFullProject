package com.example.maintainmore.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maintainmore.Adapters.ImageSlideAdapter;
import com.example.maintainmore.Adapters.ServicesAdapter;
import com.example.maintainmore.Models.CardModels;
import com.example.maintainmore.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements ServicesAdapter.viewHolder.OnServiceClickListener {



    public HomeFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView_PersonalServices, recyclerView_HomeServices, recyclerView_HomeAppliances;
    SliderView imageSliderCarousel;

    ArrayList<CardModels> PersonalServiceCardModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSliderCarousel = view.findViewById(R.id.imageSliderCarousel);
        recyclerView_PersonalServices = view.findViewById(R.id.recycleView_PersonalServices);
        recyclerView_HomeServices = view.findViewById(R.id.recycleView_HomeServices);
        recyclerView_HomeAppliances = view.findViewById(R.id.recycleView_HomeAppliances);



        ArrayList<CardModels> imageView = new ArrayList<>();

        imageView.add(new CardModels(R.drawable.image0,""));
        imageView.add(new CardModels(R.drawable.image1,""));
        imageView.add(new CardModels(R.drawable.image2,""));
        imageView.add(new CardModels(R.drawable.image3,""));
        imageView.add(new CardModels(R.drawable.image4,""));

        ImageSlideAdapter slideAdapter = new ImageSlideAdapter(imageView, getContext());
        imageSliderCarousel.setSliderAdapter(slideAdapter);
        imageSliderCarousel.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        imageSliderCarousel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSliderCarousel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);

        imageSliderCarousel.setAutoCycle(true);
        imageSliderCarousel.startAutoCycle();




        PersonalServiceCardModels.add(new CardModels(R.drawable.grapefruit, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google is a service"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        PersonalServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));

        ServicesAdapter servicesAdapter = new ServicesAdapter(PersonalServiceCardModels, getContext(), this);
        recyclerView_PersonalServices.setAdapter(servicesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false );
        recyclerView_PersonalServices.setLayoutManager(linearLayoutManager);




        ArrayList<CardModels> HomeServiceCardModels = new ArrayList<>();

        HomeServiceCardModels.add(new CardModels(R.drawable.grapefruit, "Google"));
        HomeServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        HomeServiceCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google is a service"));

        ServicesAdapter homeServicesAdapter = new ServicesAdapter(HomeServiceCardModels, getContext(),this);
        recyclerView_HomeServices.setAdapter(homeServicesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false );
        recyclerView_HomeServices.setLayoutManager(layoutManager);


        ArrayList<CardModels> HomeAppliancesCardModels = new ArrayList<>();

        HomeAppliancesCardModels.add(new CardModels(R.drawable.grapefruit, "Google"));
        HomeAppliancesCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google"));
        HomeAppliancesCardModels.add(new CardModels(R.drawable.common_google_signin_btn_icon_dark, "Google is a service"));

        ServicesAdapter homeAppliancesAdapter= new ServicesAdapter(HomeAppliancesCardModels, getContext(),this);
        recyclerView_HomeAppliances.setAdapter(homeAppliancesAdapter);

        LinearLayoutManager HomeAppliancesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false );
        recyclerView_HomeAppliances.setLayoutManager(HomeAppliancesLayoutManager);



        return view;
    }


    @Override
    public void onServiceClick(int position) {
        String name = PersonalServiceCardModels.get(position).getName();

        Toast.makeText(getContext(),"Item Clicked  " + position + " " + name , Toast.LENGTH_SHORT).show();
    }
}