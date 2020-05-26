package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication1.Model.Createclass;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication1.Adapter.createdclassadapter.Extraclassid;
import static com.example.myapplication1.Adapter.createdclassadapter.Extraname;
import static com.example.myapplication1.Adapter.createdclassadapter.Extrasession;
import static com.example.myapplication1.Adapter.createdclassadapter.Extratutorial;
import static com.example.myapplication1.Adapter.createdclassadapter.extravalue;

public class CreateClass extends AppCompatActivity {
public TextInputLayout classname,section,tutorialgrp;
public Button create;
public String name,session,ttrl,classid;
public int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        //Get Bundle
       Intent intent=getIntent();
            name= intent.getStringExtra(Extraname);
            session=intent.getStringExtra(Extrasession);
            ttrl=intent.getStringExtra(Extratutorial);
            classid=intent.getStringExtra(Extraclassid);
            value=intent.getIntExtra(extravalue,-1);

        //Layout
        classname=findViewById(R.id.classname);
        section=findViewById(R.id.section);
        tutorialgrp=findViewById(R.id.tutorialgrp);
        create=findViewById(R.id.create_class);

        //Get Token
        SharedPreferences sharedPreferencetoken =getSharedPreferences("userdata", Context.MODE_PRIVATE);
        final String token = sharedPreferencetoken.getString("Token", null);
        //Retrofit
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);

        if(value>=0){
            classname.getEditText().setText(name);
            section.getEditText().setText(session);
            tutorialgrp.getEditText().setText(ttrl);
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validation() == true) {
                        Createclass createclass1 = new Createclass(classname.getEditText().getText().toString().trim(), section.getEditText().getText().toString().trim(), tutorialgrp.getEditText().getText().toString().trim());
                        Call<Createclass> call = jsonPlaceHolderAPi.editclasses("Bearer " + token, classid, createclass1);
                        call.enqueue(new Callback<Createclass>() {
                            @Override
                            public void onResponse(Call<Createclass> call, Response<Createclass> response) {
                                if (response.code() >= 400) {
                                    if (!response.isSuccessful()) {
                                        JSONObject jsonobject = null;
                                        try {
                                            try {
                                                jsonobject = new JSONObject(response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }


                                            String message = jsonobject.getString("err");
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Succesfully Edit", Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Createclass> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }else {

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (validation()==true){

                        Createclass createclass1 = new Createclass(classname.getEditText().getText().toString().trim(), section.getEditText().getText().toString().trim(), tutorialgrp.getEditText().getText().toString().trim());
                        Call<Createclass> call = jsonPlaceHolderAPi.postclass("Bearer " + token, createclass1);
                        call.enqueue(new Callback<Createclass>() {
                            @Override
                            public void onResponse(Call<Createclass> call, Response<Createclass> response) {
                                if (response.code() >= 400) {
                                    if (!response.isSuccessful()) {
                                        JSONObject jsonobject = null;
                                        try {
                                            try {
                                                jsonobject = new JSONObject(response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }


                                            String message = jsonobject.getString("err");
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Succesfully Create", Toast.LENGTH_SHORT).show();

                                    finish();
                                }

                            }

                            @Override
                            public void onFailure(Call<Createclass> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }
    }
    public boolean validation(){
        if(classname.getEditText().getText().toString().trim().isEmpty()){
            classname.setError("Cannot be blank");
            return false;
        } if(section.getEditText().getText().toString().trim().isEmpty()){
            section.setError("Cannot be blank");
            return false;

        } if(tutorialgrp.getEditText().getText().toString().trim().isEmpty()){
            tutorialgrp.setError("Cannot be blank");
            return false;

        }
        else {
            return true;
        }
}
}
