package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Adapter.SectionPagerAdapter;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.PlayedReport;
import com.example.myapplication1.R;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayedReportDetail extends AppCompatActivity {
    private TextView title,date,hostername;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_played_report_detail);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("reportid", 0);
        String quizid=pref.getString("report_id",null);

        title=findViewById(R.id.textView7);
        date=findViewById(R.id.textView8);
        hostername=findViewById(R.id.textview9);


        viewPager=findViewById(R.id.viewPager4);
        tabLayout=findViewById(R.id.tabLayout4);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);

        SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);
        Call<PlayedReport>call1=jsonPlaceHolderAPi.getPlayedReportDetail("Bearer "+token,quizid);
        call1.enqueue(new Callback<PlayedReport>() {
            @Override
            public void onResponse(Call<PlayedReport> call, Response<PlayedReport> response) {
                if (response.isSuccessful() && response.body() != null) {

                    title.setText(response.body().getGameName());
                    date.setText(response.body().getPlayedDate());
                    hostername.setText("Hosted By : "+response.body().getHosterName());

                }else{
                    Toast.makeText(PlayedReportDetail.this,"Nothing",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayedReport> call, Throwable t) {

            }
        });
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
        SectionPagerAdapter adapter=new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlayedOverallFragment(),"Overall");
        adapter.addFragment(new PlayedQuestionFragement(),"Questions");


        viewPager.setAdapter(adapter);

    }



}
