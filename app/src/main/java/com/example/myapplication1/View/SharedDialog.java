package com.example.myapplication1.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Share;
import com.example.myapplication1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SharedDialog extends AppCompatDialogFragment {
public Button sharequiz;
public TextView recipient;
    private ExampleDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        Bundle bundle=getArguments();
        final String id=bundle.getString("quizid");
        View view =inflater.inflate(R.layout.activity_shared_dialog,null);
        recipient=view.findViewById(R.id.recipient);
        builder.setView(view).setNegativeButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(recipient.getText().toString().trim().isEmpty()) {
                    recipient.setError("Cannot be blank");
                }
                if(!recipient.getText().toString().trim().isEmpty()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    String token = sharedPreferencetoken.getString("Token", null);
                    Share share = new Share(id, recipient.getText().toString().trim());
                    JsonPlaceHolderAPi jsonPlaceHolderAPi = retrofit.create(JsonPlaceHolderAPi.class);
                    Call<Share> call = jsonPlaceHolderAPi.postshared("Bearer " + token, share);
                    call.enqueue(new Callback<Share>() {
                        @Override
                        public void onResponse(Call<Share> call, Response<Share> response) {
                            if(response.code()>=400){
                                if(!response.isSuccessful()) {
                                    JSONObject jsonobject=null;
                                    try{
                                        try {
                                            jsonobject=new JSONObject(response.errorBody().string());
                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }

                                        Boolean shared=jsonobject.getBoolean("isShared");
                                        String message=jsonobject.getString("err");

                                        listener.applyTexts(shared, message);
                                        dismiss();

                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                }
                            }else{
//                                Toast.makeText(getActivity(), "Succesfully Share", Toast.LENGTH_SHORT).show();
                                listener.applyTexts(true,"Share Succesful");
                                dismiss();
                            }


                        }

                        @Override
                        public void onFailure(Call<Share> call, Throwable t) {
                            Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



        /*sharequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit =new Retrofit.Builder()
                        .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SharedPreferences sharedPreferencetoken =getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                String token = sharedPreferencetoken.getString("Token", null);
                Share share=new Share(id,recipient.getText().toString().trim());
                JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
                Call<Share> call=jsonPlaceHolderAPi.postshared("Bearer "+token,share);
                call.enqueue(new Callback<Share>() {
                    @Override
                    public void onResponse(Call<Share> call, Response<Share> response) {
                        if(response.body().isShared()==true){
                            Toast.makeText(getActivity(),"Shared Successfully",Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getActivity(),"Shared Failed",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Share> call, Throwable t) {
                        Toast.makeText(getActivity(),"Connection Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/




        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void applyTexts(Boolean ishared,String errormessage);
    }
}
