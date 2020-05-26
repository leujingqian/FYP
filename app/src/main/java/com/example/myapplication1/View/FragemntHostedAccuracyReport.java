package com.example.myapplication1.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
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

import com.example.myapplication1.Adapter.questionaccuracyadapter;
import com.example.myapplication1.Adapter.scoreboard_adapter;
import com.example.myapplication1.Model.HR_HosterReport;
import com.example.myapplication1.Model.HR_Question;
import com.example.myapplication1.Model.HR_Reports;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragemntHostedAccuracyReport extends Fragment {
    private RecyclerView mrecyclerView;
    private ArrayList<HR_Question>accuracylist=new ArrayList<>();
    private questionaccuracyadapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment__hosted_accuracy_report, container, false);
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
        Call<HR_Reports> call1 = jsonPlaceHolderAPi.getReportsDetail("Bearer " + token,quizid);
       call1.enqueue(new Callback<HR_Reports> () {
           @Override
           public void onResponse(Call<HR_Reports> call, Response<HR_Reports>  response) {
               if (response.isSuccessful() && response.body()!=null) {
                   accuracylist = new ArrayList<>(response.body().getHosterReport().getQuestions());
                   adapter=new questionaccuracyadapter(accuracylist,getActivity());
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
