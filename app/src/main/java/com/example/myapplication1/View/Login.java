package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    public String email;
    public String pass;
    public boolean data;
    public TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            if(checkpreviouslogin()==false){
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else if(checkpreviouslogin()==true) {

            final EditText mail = findViewById(R.id.email);
            final EditText password = findViewById(R.id.password);
            Button login = findViewById(R.id.login);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            register=findViewById(R.id.jumptoregister);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Login.this,Register.class);
                    startActivity(i);                }
            });
            final JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = mail.getText().toString().trim();
                    pass = password.getText().toString().trim();
                    if (email.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else {
                        com.example.myapplication1.Model.Login loginuser = new com.example.myapplication1.Model.Login(email, pass);
                        Call<com.example.myapplication1.Model.Login> call = jsonPlaceHolderAPi.callLogin(loginuser);
                        call.enqueue(new Callback<com.example.myapplication1.Model.Login>() {
                            @Override
                            public void onResponse(Call<com.example.myapplication1.Model.Login> call, Response<com.example.myapplication1.Model.Login> response) {
                                if (response.code() == 400) {
                                    JSONObject jsonobject = null;
                                    try {
                                        try {
                                            jsonobject = new JSONObject(response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Boolean logged = jsonobject.getBoolean("isLogged");
                                        String message = jsonobject.getString("message");
                                        Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(Login.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Username", response.body().getUser().getName());
                                    editor.putString("Token", response.body().getToken());
                                    editor.putString("Id", response.body().getUser().getId());
                                    editor.putString("email", response.body().getUser().getEmail());
                                    editor.apply();
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<com.example.myapplication1.Model.Login> call, Throwable t) {

                            }
                        });


                    }
                }
            });
        }

    }
    public boolean checkpreviouslogin(){
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String token=sharedPreferences.getString("Token",null);
        if(token==null){
            data=true;
        }else{
            data=false;
        }
            return data;
    }
}
