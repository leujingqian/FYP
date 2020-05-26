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

import com.example.myapplication1.Adapter.played_questionadapter;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.PlayedReport;
import com.example.myapplication1.Model.Played_QUestion;
import com.example.myapplication1.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlayedQuestionFragement extends Fragment {
    private RecyclerView mrecyclerView;
    private ArrayList<Played_QUestion> questionlist=new ArrayList<>();
    private played_questionadapter adapter;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v= inflater.inflate(R.layout.fragment_played_question_fragement, container, false);
            mrecyclerView = (RecyclerView) v.findViewById(R.id.questionaccuracy_recyclerview);
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
            Call<PlayedReport>call=jsonPlaceHolderAPi.getPlayedReportDetail("Bearer "+token,quizid);
            call.enqueue(new Callback<PlayedReport>() {
                @Override
                public void onResponse(Call<PlayedReport> call, Response<PlayedReport> response) {
                    if (response.isSuccessful() && response.body()!=null) {
                        questionlist = new ArrayList<>(response.body().getQuestions());
                        adapter=new played_questionadapter(questionlist,getActivity());
                        mrecyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onFailure(Call<PlayedReport> call, Throwable t) {

                }
            });
            return v;
        }








}
