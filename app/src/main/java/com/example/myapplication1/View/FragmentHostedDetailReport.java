package com.example.myapplication1.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication1.Adapter.scoreboard_adapter;
import com.example.myapplication1.Model.HR_Reports;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Scoreboard;
import com.example.myapplication1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentHostedDetailReport extends Fragment {
private RecyclerView mrecyclerview;
private ArrayList<Scoreboard> scoreboardslist=new ArrayList<>();
private scoreboard_adapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_hosted_detail_report, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("reportid", 0);
        String quizid=pref.getString("report_id",null);
        mrecyclerview=(RecyclerView) v.findViewById(R.id.scoreboard_recyclerview);
        mrecyclerview.setLayoutManager((new LinearLayoutManager(getActivity())));
        mrecyclerview.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);

        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);

        final Button accuracy=v.findViewById(R.id.accuracy_button);
        final Button questions=v.findViewById(R.id.Questions_button);
        final Button players=v.findViewById(R.id.Players_button);
        Call<HR_Reports> call1 = jsonPlaceHolderAPi.getReportsDetail("Bearer " + token,quizid);
        call1.enqueue(new Callback<HR_Reports>() {
            @Override
            public void onResponse(Call<HR_Reports>call1, Response<HR_Reports> response) {
                if (response.isSuccessful() && response.body() != null) {

                    accuracy.setText(response.body().getHosterReport().getAccuracy()+"");
                    questions.setText(response.body().getHosterReport().getQuestions().size()+"");
                    players.setText(response.body().getHosterReport().getPlayerResults().size()+"");
                    scoreboardslist=new ArrayList<>(response.body().getHosterReport().getScoreboard());
                    adapter=new scoreboard_adapter(scoreboardslist,getActivity());
                    mrecyclerview.setAdapter(adapter);



                }else{
                    Toast.makeText(getActivity(),"Nothing",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HR_Reports> call1, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


}
