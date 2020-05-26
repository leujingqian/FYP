package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Scoreboard;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class scoreboard_adapter extends RecyclerView.Adapter<scoreboard_adapter.ViewHolder> {

    private ArrayList<Scoreboard> scoreboardslist;
    private Context context;

    public scoreboard_adapter(ArrayList<Scoreboard>scoreboardslist,Context context){
        this.context=context;
        this.scoreboardslist=scoreboardslist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView scoreboardname;
        public TextView scoreboardpoints;

        public ViewHolder(View v){
            super(v);
            scoreboardname=v.findViewById(R.id.scorebaord_name);
            scoreboardpoints=v.findViewById(R.id.scorebaord_points);
        }
    }

    @Override
    public int getItemCount() {
        return scoreboardslist.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scoreboard currentitem=scoreboardslist.get(position);
        holder.scoreboardpoints.setText(currentitem.getPoints()+"");
        holder.scoreboardname.setText((position+1)+".  "+currentitem.getName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreboard_cardview,parent,false);
        return new scoreboard_adapter.ViewHolder(view);
    }
}
