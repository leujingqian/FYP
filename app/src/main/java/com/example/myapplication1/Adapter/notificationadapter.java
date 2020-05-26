package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.notification_model;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class notificationadapter extends RecyclerView.Adapter<notificationadapter.ViewHolder> {

    private ArrayList<notification_model> notificationlist;
    private Context context;


    public notificationadapter(ArrayList<notification_model> notificationlist,Context context){
        this.context=context;
        this.notificationlist=notificationlist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView content;
        public TextView date;
        public ViewHolder(View v){
            super(v);
            content=v.findViewById(R.id.notification_cont);
            date=v.findViewById(R.id.date);
        }
    }

    @Override
    public int getItemCount() {
        return notificationlist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        notification_model currentItem=notificationlist.get(position);
        holder.date.setText(currentItem.getTime_stamp());
        holder.content.setText(currentItem.getContent());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_cardview,parent,false);
        return new notificationadapter.ViewHolder(view);
    }
}
