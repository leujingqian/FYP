package com.example.myapplication1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Question;
import com.example.myapplication1.Model.QuizQuestion;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class showquestionadapter extends RecyclerView.Adapter<showquestionadapter.ViewHolder> {
 private ArrayList<QuizQuestion> questionlist;
 private Context context;

 public showquestionadapter(ArrayList<QuizQuestion>questionlist,Context context){
     this.context=context;
     this.questionlist=questionlist;
 }
 public class ViewHolder extends RecyclerView.ViewHolder{
     public TextView showquestion;
     public TextView number;
     public ViewHolder(View v){
         super(v);
         showquestion=v.findViewById(R.id.showquestion);
         number=v.findViewById(R.id.number);
     }
 }

    @Override
    public int getItemCount() {
        return questionlist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizQuestion currentitem=questionlist.get(position);
        holder.number.setText((position+1)+". ");
        holder.showquestion.setText(currentitem.getQuestion());
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#A9A9A9"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
           /* holder.showquestion.setTextColor(Color.parseColor("#FFFFFF"));
            holder.number.setTextColor(Color.parseColor("#FFFFFF"));*/
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showquestion_cardview,parent,false);
        return new showquestionadapter.ViewHolder(view);
    }
}
