package com.example.myapplication1.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication1.Adapter.questionaccuracyadapter;
import com.example.myapplication1.Adapter.reportplayeradapter;
import com.example.myapplication1.Model.HR_PlayerResults;
import com.example.myapplication1.Model.HR_Reports;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHostedPlayerReport extends Fragment {
    private RecyclerView mrecyclerView;
    private ArrayList<HR_PlayerResults> playerlist=new ArrayList<>();
    private reportplayeradapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_fragment_hosted_player_report, container, false);

        mrecyclerView = (RecyclerView) v.findViewById(R.id.playerrresults_recycler_view);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView.setHasFixedSize(true);
        SharedPreferences pref = getActivity().getSharedPreferences("reportid", 0);
        String quizid=pref.getString("report_id",null);
        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
        Call<HR_Reports> call1 = jsonPlaceHolderAPi.getReportsDetail("Bearer " + token,quizid);
        call1.enqueue(new Callback<HR_Reports>() {
            @Override
            public void onResponse(Call<HR_Reports> call, Response<HR_Reports> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    playerlist = new ArrayList<>(response.body().getHosterReport().getPlayerResults());
                    adapter=new reportplayeradapter(playerlist,getActivity());
                    mrecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<HR_Reports> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
}
