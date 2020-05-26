package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Adapter.DataAdapter;
import com.example.myapplication1.Adapter.notificationadapter;
import com.example.myapplication1.Model.notification_model;
import com.example.myapplication1.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class notification extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private ArrayList<notification_model> notificationlist=new ArrayList<>();
    private notificationadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String notification=getIntent().getStringExtra("listnotification");
        TextView alert=findViewById(R.id.alert);
        Gson gson=new Gson();
        Type type=new TypeToken<List<notification_model>>(){}.getType();
        notificationlist=gson.fromJson(notification,type);
        Collections.reverse(notificationlist);
        if(notificationlist.isEmpty()){
            alert.setVisibility(View.VISIBLE);
        }
        else
            alert.setVisibility(View.INVISIBLE);
        mrecyclerView = (RecyclerView) findViewById(R.id.notification_recycler_view);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setHasFixedSize(true);

        adapter=new notificationadapter(notificationlist,getApplicationContext());
        mrecyclerView.setAdapter(adapter);




    }
}
