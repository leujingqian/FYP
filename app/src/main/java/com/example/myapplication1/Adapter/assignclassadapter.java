package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class assignclassadapter extends RecyclerView.Adapter<assignclassadapter.ViewHolder> {
    private ArrayList<Classes> classeslist;
    private Context context;
    private assignclassadapter.onItemCheckListener mListener;

    public interface onItemCheckListener {
        void onItemCheck(Classes classes);
        void onitemUncheck(Classes classes);
    }
    public void setOnItemClickListener(assignclassadapter.onItemCheckListener listener) {
        mListener = listener;
    }

    public assignclassadapter(ArrayList<Classes>classeslist, Context context){
        this.context=context;
        this.classeslist=classeslist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView assignclass;
        public CheckBox checkBox;

        public ViewHolder(View v){
            super(v);
            checkBox=v.findViewById(R.id.assign_checkbox);
            checkBox.setClickable(false);
            assignclass=v.findViewById(R.id.assign_classname);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                   checkBox.setChecked(!checkBox.isChecked());

                   if(checkBox.isChecked()){
                        mListener.onItemCheck(classeslist.get(position));
                   }else{
                       mListener.onitemUncheck(classeslist.get(position));
                   }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Classes currentItem=classeslist.get(position);
        holder.assignclass.setText(currentItem.getName());



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_cardview,parent,false);
        return new assignclassadapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return  classeslist.size();
    }
}
