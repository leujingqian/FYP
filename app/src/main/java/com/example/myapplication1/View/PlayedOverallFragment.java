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
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.PlayedReport;
import com.example.myapplication1.Model.Scoreboard;
import com.example.myapplication1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlayedOverallFragment extends Fragment {
    private RecyclerView mrecyclerview;
    private ArrayList<Scoreboard> scoreboardslist=new ArrayList<>();
    private scoreboard_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_played_overall, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("reportid", 0);
        String quizid=pref.getString("report_id",null);
        mrecyclerview=(RecyclerView) v.findViewById(R.id.scoreboard_recyclerview);
        mrecyclerview.setLayoutManager((new LinearLayoutManager(getActivity())));
        mrecyclerview.setHasFixedSize(true);
        final Button accuracy=v.findViewById(R.id.accuracy_button);
        final Button questions=v.findViewById(R.id.Questions_button);
        final Button players=v.findViewById(R.id.Players_button);
        final Button correct=v.findViewById(R.id.played_correctbutton);
        final Button incorrect=v.findViewById(R.id.played_incorrectbutton);
        final Button unattempt=v.findViewById(R.id.played_unattemptbutton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);

        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);

        Call<PlayedReport> call1=jsonPlaceHolderAPi.getPlayedReportDetail("Bearer "+token,quizid);
        call1.enqueue(new Callback<PlayedReport>() {
            @Override
            public void onResponse(Call<PlayedReport> call, Response<PlayedReport> response) {
                if (response.isSuccessful() && response.body() != null) {

                    accuracy.setText(response.body().getPoints() + "");
                    int correctanswer=response.body().getCorrect();
                    int totalquestion=response.body().getQuestions().size();
                    float accuracy=((float)correctanswer/(float)totalquestion)*100;
                    questions.setText(""+(int)Math.floor(accuracy));
                    players.setText(response.body().getRank() + "");
                    correct.setText(response.body().getCorrect()+"");
                    incorrect.setText(response.body().getIncorrect()+"");
                    unattempt.setText(response.body().getUnattempted()+"");
                    scoreboardslist = new ArrayList<>(response.body().getScoreboard());
                    adapter = new scoreboard_adapter(scoreboardslist, getActivity());
                    mrecyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PlayedReport> call, Throwable t) {

            }
        });
        return v;
    }






}
