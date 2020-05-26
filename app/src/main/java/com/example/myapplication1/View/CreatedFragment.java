package com.example.myapplication1.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication1.Adapter.DataAdapter;
import com.example.myapplication1.Adapter.HostedReportAdapter;
import com.example.myapplication1.Adapter.createdclassadapter;
import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Member;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.Model.Reports;
import com.example.myapplication1.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatedFragment extends Fragment implements createdclassadapter.OnItemClickListener  {
    private RecyclerView mrecyclerView;
    private ArrayList<Classes> classlist=new ArrayList<>();
    private createdclassadapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragement_created, container, false);
        mrecyclerView = (RecyclerView) v.findViewById(R.id.created_recycler_view);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView.setHasFixedSize(true);
        swipeRefreshLayout=v.findViewById(R.id.swipetorefresh);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String token = sharedPreferencetoken.getString("Token", null);
        final String userid=sharedPreferencetoken.getString("Id", null);
        Call<List<Classes>> call1 = jsonPlaceHolderAPi.getclasses("Bearer " + token);
        call1.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    classlist = new ArrayList<>();
                    for (int i = 0; i <response.body().size(); i++) {

                        if(response.body().get(i).getAdmins().get(0).getId().equals(userid)){
                            classlist.add(response.body().get(i));
                        }

                        adapter = new createdclassadapter(classlist, getActivity());
                        mrecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(CreatedFragment.this);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"dfdfdfdf",Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
                SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                String token = sharedPreferencetoken.getString("Token", null);
                final String userid=sharedPreferencetoken.getString("Id", null);
                Call<List<Classes>> call1 = jsonPlaceHolderAPi.getclasses("Bearer " + token);
                call1.enqueue(new Callback<List<Classes>>() {
                    @Override
                    public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            classlist = new ArrayList<>();
                            for (int i = 0; i <response.body().size(); i++) {

                                if(response.body().get(i).getAdmins().get(0).getId().equals(userid)){
                                    classlist.add(response.body().get(i));
                                }

                                adapter = new createdclassadapter(classlist, getActivity());
                                mrecyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(CreatedFragment.this);


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Classes>> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return v;
    }


    @Override
    public void onItemClick(int position) {
        Classes clickeditem=classlist.get(position);
        Intent intent=new Intent(getContext(),member_list_view.class);
        intent.putExtra("id",clickeditem.getId());
        intent.putExtra("classid",clickeditem.getClassId());
        intent.putExtra("created_adminname",clickeditem.getAdmins().get(0).getName());
       startActivity(intent);

    }
}
