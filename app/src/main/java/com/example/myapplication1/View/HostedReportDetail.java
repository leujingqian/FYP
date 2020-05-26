package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Model.HR_Reports;
import com.example.myapplication1.Adapter.SectionPagerAdapter;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;

import com.example.myapplication1.R;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication1.View.HostedFragment.EXTRA_reportID;


public class HostedReportDetail extends AppCompatActivity {
    private TextView title,date;
    public Button accuracy,report;
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosted_report_detail);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("reportid", 0);
        String quizid=pref.getString("report_id",null);

        title=findViewById(R.id.textView4);
        date=findViewById(R.id.textView6);

        viewPager=findViewById(R.id.viewPager3);
        tabLayout=findViewById(R.id.tabLayout3);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);

        SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);

        Call<HR_Reports> call1 = jsonPlaceHolderAPi.getReportsDetail("Bearer " + token,quizid);
        call1.enqueue(new Callback <HR_Reports>() {
            @Override
            public void onResponse(Call<HR_Reports>call1, Response<HR_Reports> response) {
                if (response.isSuccessful() && response.body() != null) {

                       title.setText(response.body().getHosterReport().getGameName());
                        date.setText(response.body().getHosterReport().getHostedDate());

                }else{
                    Toast.makeText(HostedReportDetail.this,"Nothing",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HR_Reports> call1, Throwable t) {
                Toast.makeText(HostedReportDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        adapter.addFragment(new FragmentHostedDetailReport(),"Report");
        adapter.addFragment(new FragemntHostedAccuracyReport(),"Accuracy");
        adapter.addFragment(new FragmentHostedPlayerReport(),"Players");


        viewPager.setAdapter(adapter);

    }
}
