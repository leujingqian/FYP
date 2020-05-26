package com.example.myapplication1.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication1.Adapter.HostedReportAdapter;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;


import com.example.myapplication1.Model.Reports;
import com.example.myapplication1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HostedFragment extends Fragment implements HostedReportAdapter.OnItemClickListener {
    public TextView tv;
    public String score;
    public String content;
    private RecyclerView mrecyclerreview;
    private ArrayList<Reports> hostedreports = new ArrayList<>();
    private HostedReportAdapter adapter;
    public static final String EXTRA_reportID = "report_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_hosted_fragment, container, false);
        mrecyclerreview = (RecyclerView) v.findViewById(R.id.card_recycler_view);
        mrecyclerreview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerreview.setHasFixedSize(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String token = sharedPreferencetoken.getString("Token", null);
        Call<List<Reports>> call1 = jsonPlaceHolderAPi.getReports("Bearer " + token);
        call1.enqueue(new Callback<List<Reports>>() {
            @Override
            public void onResponse(Call<List<Reports>> call, Response<List<Reports>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hostedreports = new ArrayList<>(response.body());
                    Collections.reverse(hostedreports   );
                    adapter = new HostedReportAdapter(hostedreports, getActivity());
                    mrecyclerreview.setAdapter(adapter);
                    adapter.setOnItemClickListener(HostedFragment.this);
                }
            }

            @Override
            public void onFailure(Call<List<Reports>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;


    }

    @Override
    public void onItemClick(int position) {
        Intent hostedreportintent = new Intent(getActivity(), HostedReportDetail.class);
        Reports clickedItem = hostedreports.get(position);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("reportid", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("report_id",clickedItem.get_id());
        editor.commit();
        startActivity(hostedreportintent);
    }
}
