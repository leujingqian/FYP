package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication1.Model.Createclass;
import com.example.myapplication1.Model.Joinclass;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinClass extends AppCompatActivity {
public TextInputLayout classcode;
public Button joinclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);

        classcode=findViewById(R.id.classcode);
        joinclass=findViewById(R.id.join_class);

        joinclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classcode.getEditText().getText().toString().trim().isEmpty()){
                    classcode.setError("Cannot be Blank");
                }else{
                    SharedPreferences sharedPreferencetoken =getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    String token = sharedPreferencetoken.getString("Token", null);
                    Retrofit retrofit =new Retrofit.Builder()
                            .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
                    Joinclass joinclass=new Joinclass(classcode.getEditText().getText().toString().trim());
                    Call<Joinclass> call=jsonPlaceHolderAPi.joinclass("Bearer "+token,joinclass);
                    call.enqueue(new Callback<Joinclass>() {
                        @Override
                        public void onResponse(Call<Joinclass> call, Response<Joinclass> response) {
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
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();


                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                }

                            }else if(response.code()==200)
                            {
                                Toast.makeText(getApplicationContext(),"Succesfully Join",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Joinclass> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
