package com.example.myapplication1.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.R;
import com.example.myapplication1.Adapter.assignclassadapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingDialog extends AppCompatDialogFragment implements assignclassadapter.onItemCheckListener  {
    private Switch shufflequestion;
    private Switch shufflechoices;
    private Switch questiontimer;
    private Switch automove;
    private RecyclerView mrecyclerView;
    private ArrayList<Classes> classlist=new ArrayList<>();
    private assignclassadapter adapter;
    private List<Classes> currentSelectedItems = new ArrayList<>();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view =inflater.inflate(R.layout.activity_setting_dialog,null);
        shufflequestion= view.findViewById(R.id.shufflequestions);
        shufflechoices= view.findViewById(R.id.shufflechoices);
        questiontimer=view.findViewById(R.id.questiontimer);
        automove=view.findViewById(R.id.movethrough);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.assignclass_recyclerview);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView.setHasFixedSize(true);
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

                        adapter = new assignclassadapter(classlist, getActivity());
                        mrecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(SettingDialog.this);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });
        Bundle bundle=getArguments();
        final String id=bundle.getString("quizid");
        builder.setView(view)
                .setTitle("")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                Gson gson=new Gson();
                                String assign=gson.toJson(currentSelectedItems);

                                Intent intent =new Intent(getActivity(),HostingGame.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("quizid",id);
                                if(shufflequestion.isChecked()){
                                    bundle.putBoolean("shufflequestion",true);
                                }
                                 if(shufflechoices.isChecked()){
                                 bundle.putBoolean("shufflechoices",true);
                                    }
                                    if(questiontimer.isChecked()){
                                    bundle.putBoolean("questiontimer",true);
                                     }
                                    if(automove.isChecked()){
                                    bundle.putBoolean("automove",true);
                                    }
                                    bundle.putString("assignclass",assign);
                                 intent.putExtras(bundle);
                                 startActivity(intent);
                    }
                });
        return builder.create();
    }

    @Override
    public void onItemCheck(Classes classes) {
        currentSelectedItems.add(classes);
        Toast.makeText(getActivity(),currentSelectedItems.get(0).getName()+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onitemUncheck(Classes classes) {
        currentSelectedItems.remove(classes);
        Toast.makeText(getActivity(),currentSelectedItems.size()+"",Toast.LENGTH_SHORT).show();
    }
}
