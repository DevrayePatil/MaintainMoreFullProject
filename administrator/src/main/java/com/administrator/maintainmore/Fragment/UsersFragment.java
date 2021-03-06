package com.administrator.maintainmore.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.maintainmore.Adapters.ServicePagerAdapter;
import com.administrator.maintainmore.R;
import com.google.android.material.tabs.TabLayout;


public class UsersFragment extends Fragment {



    public UsersFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setViewPager(ViewPager viewPager) {

        ServicePagerAdapter pagerAdapter = new ServicePagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(new UserFragment(),"Users");
        pagerAdapter.addFragment(new TechnicianFragment(),"Technician");

        viewPager.setAdapter(pagerAdapter);
    }
}