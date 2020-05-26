package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Adapter.createdclassadapter;
import com.example.myapplication1.Adapter.memberlistadapter;
import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.createclasses;
import com.example.myapplication1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class member_list_view extends AppCompatActivity{
    private RecyclerView mrecyclerView;
    private ArrayList member=new ArrayList<>();
    private memberlistadapter adapter;
    private String id,memberid,classid,adminname;
    private SwipeRefreshLayout swipeRefreshLayout;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://collaborative-learning-system.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list_view);
        swipeRefreshLayout=findViewById(R.id.memberlistview_swipetorefreshlayout);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("memberid"));
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        classid=intent.getStringExtra("classid");
        adminname=intent.getStringExtra("created_adminname");
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
                    adapter = new memberlistadapter(member,getApplicationContext());
                    mrecyclerView.setAdapter(adapter);
                    admin.setText(adminname);
                    adapter.setOnItemClickListener(new memberlistadapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(int posiition) {
                            deletemember();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<createclasses> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
                SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
                String token = sharedPreferencetoken.getString("Token", null);
                Call<createclasses> call1 = jsonPlaceHolderAPi.getmember("Bearer " + token,id);
                call1.enqueue(new Callback<createclasses>() {
                    @Override
                    public void onResponse(Call<createclasses> call, Response<createclasses> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            member =new ArrayList<>(response.body().getMyClass().getMembers());
                            adapter = new memberlistadapter(member,getApplicationContext());
                            mrecyclerView.setAdapter(adapter);
                            admin.setText(adminname);
                            adapter.setOnItemClickListener(new memberlistadapter.OnItemClickListener(){
                                @Override
                                public void onItemClick(int posiition) {
                                    deletemember();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<createclasses> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }


    public void deletemember() {
        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
        SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String token = sharedPreferencetoken.getString("Token", null);
        Classes classes=new Classes(classid);
        Call<Classes> call1 = jsonPlaceHolderAPi.deletemember("Bearer " + token,memberid,classes);
        call1.enqueue(new Callback<Classes>() {
            @Override
            public void onResponse(Call<Classes> call, Response<Classes> response) {
                if(response.code()>=400){
                    if(!response.isSuccessful()) {
                        JSONObject jsonobject=null;
                        try{
                            try {
                                jsonobject=new JSONObject(response.errorBody().string());
                            }catch (IOException e){
                                e.printStackTrace();
                            }


                            String message=jsonobject.getString("err");
                            Toast.makeText(getApplicationContext(),"Click one more time to delete",Toast.LENGTH_SHORT).show();


                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                }else
                {
                    Toast.makeText(getApplicationContext(),"Succesfully delete",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Classes> call, Throwable t) {

            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
             memberid = intent.getStringExtra("id");

            Toast.makeText(member_list_view.this,"Click one more time to delete",Toast.LENGTH_SHORT).show();
        }
    };
}
