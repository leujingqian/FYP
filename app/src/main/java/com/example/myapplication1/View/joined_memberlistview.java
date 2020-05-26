package com.example.myapplication1.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Adapter.joined_memberlistadapter;
import com.example.myapplication1.Adapter.memberlistadapter;
import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.createclasses;
import com.example.myapplication1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class joined_memberlistview extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private ArrayList member=new ArrayList<>();
    private joined_memberlistadapter adapter;
    private String id,memberid,classid,adminname;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://collaborative-learning-system.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list_view);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        classid=intent.getStringExtra("classid");
        adminname=intent.getStringExtra("adminame");
        mrecyclerView = findViewById(R.id.memberlist_recyclerview);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final TextView admin=findViewById(R.id.adminname);

        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
        SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);

        Call<createclasses> call1 = jsonPlaceHolderAPi.getmember("Bearer " + token,id);
        call1.enqueue(new Callback<createclasses>() {
            @Override
            public void onResponse(Call<createclasses> call, Response<createclasses> response) {
                if (response.isSuccessful() && response.body() != null) {

                    member =new ArrayList<>(response.body().getMyClass().getMembers());
                    adapter = new joined_memberlistadapter(member,getApplicationContext());
                    mrecyclerView.setAdapter(adapter);
                    admin.setText(adminname);
                    Toast.makeText(getApplicationContext(),adminname,Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<createclasses> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
