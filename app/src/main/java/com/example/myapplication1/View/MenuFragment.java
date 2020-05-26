package com.example.myapplication1.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Post;
import com.example.myapplication1.R;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {
  private TextView viewmenu,loginname,loginemail;
  public Button logout,clasroom;
  private String name,email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu,container,false);

         logout=(Button)view.findViewById(R.id.logout) ;
         clasroom=view.findViewById(R.id.classroom);
         loginemail=view.findViewById(R.id.loginemail);
         loginname=view.findViewById(R.id.loginname);


        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String name = sharedPreferencetoken.getString("Username", null);
        String email = sharedPreferencetoken.getString("email", null);

        loginemail.setText(email);
        loginname.setText(name);

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
                 final SharedPreferences.Editor editor=sharedPreferences.edit();
                 String token=sharedPreferences.getString("Tokem",null);
                 Toast.makeText(getActivity(),token,Toast.LENGTH_SHORT).show();
                 sharedPreferences.edit().clear().commit();
                 Intent i=new Intent(getActivity(),Login.class);
                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(i);

             }
         });

         clasroom.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getActivity(),classroom.class);
                 startActivity(intent);
             }
         });

return view;

    }


}
