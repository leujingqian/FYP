package com.example.myapplication1.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication1.R;
import com.google.android.material.tabs.TabLayout;


public class muquizzesFragment extends Fragment {

    public ImageButton create;

    public muquizzesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_muquizzes, container, false);
        create = (ImageButton) v.findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), createquiz.class);
                startActivity(intent);
            }

        });

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event

}


