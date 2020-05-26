package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class Register extends AppCompatActivity {
    public EditText lastname,firstname,email,password,password2;
    public Button register;
    private String name1,mail,pass,pass2,name2;
    public TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lastname=findViewById(R.id.lastname);
         firstname=findViewById(R.id.firstname);
         email=findViewById(R.id.register_email);
         password=findViewById(R.id.register_password);
         password2=findViewById(R.id.register_password2);
         register=findViewById(R.id.register);
         login=findViewById(R.id.jumptoLogin);
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
//        com.example.myapplication1.Model.Register user=new com.example.myapplication1.Model.Register(username.getText().toString().trim(),email.getText().toString().trim(),password.getText().toString().trim(),password2.getText().toString().trim());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name1 = firstname.getText().toString().trim();
                name2 = lastname.getText().toString().trim();
                mail = email.getText().toString().trim();
                pass = password.getText().toString().trim();
                pass2 = password2.getText().toString().trim();
                if (Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {


                    com.example.myapplication1.Model.Register users = new com.example.myapplication1.Model.Register(name1, name2, mail, pass, pass2);
                    Call<com.example.myapplication1.Model.Register> call = jsonPlaceHolderAPi.callRegister(users);
                    call.enqueue(new Callback<com.example.myapplication1.Model.Register>() {
                        @Override
                        public void onResponse(Call<com.example.myapplication1.Model.Register> call, Response<com.example.myapplication1.Model.Register> response) {
                        /*try{
                            String s=response.body().toString();
                            Toast.makeText(Register.this,s+response.code(),Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                            if (response.code() == 400) {
                                if (!response.isSuccessful()) {
                                    JSONObject jsonobject = null;
                                    try {
                                        try {
                                            jsonobject = new JSONObject(response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Boolean registered = jsonobject.getBoolean("isRegistered");
                                        String message = jsonobject.getString("err");

                                        Toast.makeText(Register.this, message, Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }else{
                                Toast.makeText(Register.this, "Account succesfully created", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }

                        }

                        @Override
                        public void onFailure(Call<com.example.myapplication1.Model.Register> call, Throwable t) {
                            Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                } else{
                    Toast.makeText(Register.this,"Email not valid",Toast.LENGTH_SHORT).show();
                }

            }

        });


    }
   /* private void checking(){
        name=username.getText().toString().trim();
        mail=email.getText().toString().trim();
        pass=password.getText().toString().trim();
        pass2=password2.getText().toString().trim();
        com.example.myapplication1.Model.Register users=new com.example.myapplication1.Model.Register(name,mail,pass,pass2);
        Call<Register> call=jsonPlaceHolderAPi.cr\\*/
      /*  if(name.isEmpty()){

        }

        if(mail.isEmpty()){

        }
        if(pass.isEmpty()){

        }
        if(pass2.isEmpty()){

        }
        if(!pass.equals(pass2)){

        }*/



}
