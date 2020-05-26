package com.example.myapplication1.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication1.Adapter.SectionPagerAdapter;
import com.example.myapplication1.R;
import com.google.android.material.tabs.TabLayout;

public class ReportFragment extends Fragment {

    private Intent intent;
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        viewPager=(ViewPager)v.findViewById(R.id.viewPager2);
        tabLayout=(TabLayout)v.findViewById(R.id.tabLayout2);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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
    private void setUpViewPager(ViewPager viewPager){
        SectionPagerAdapter adapter=new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HostedFragment(),"Hosted");
        adapter.addFragment(new PlayedFragment(),"Played");


        viewPager.setAdapter(adapter);

    }
}
