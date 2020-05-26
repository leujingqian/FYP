package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.HR_PlayerResults;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class reportplayeradapter extends RecyclerView.Adapter<reportplayeradapter.ViewHolder> {

    private ArrayList<HR_PlayerResults> playerlist;
    private Context context;

    public reportplayeradapter(ArrayList<HR_PlayerResults>playerlist,Context context){
        this.context=context;
        this.playerlist=playerlist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView accuracypercent,correctnum,incorrectnum,unatttemptnum,studentname;

        public ViewHolder(View v){
            super(v);
            accuracypercent=v.findViewById(R.id.studentaccuracy);
            studentname=v.findViewById(R.id.studentname);
            correctnum=v.findViewById(R.id.correctnum);
            incorrectnum=v.findViewById(R.id.incorrectnum);
            unatttemptnum=v.findViewById(R.id.unattemptnum);
        }
    }

    @Override
    public int getItemCount() {
        return playerlist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HR_PlayerResults currentItem=playerlist.get(position);
            holder.studentname.setText(currentItem.getName());
            holder.accuracypercent.setText(currentItem.getAccuracy()+"%");
            holder.correctnum.setText(currentItem.getCorrect()+"");
            holder.incorrectnum.setText(currentItem.getIncorrect()+"");
            holder.unatttemptnum.setText(currentItem.getUnattempted()+"");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.playerresults_cardview,parent,false);
        return new reportplayeradapter.ViewHolder(view);
    }
}
