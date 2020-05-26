package com.example.myapplication1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Played_QUestion;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class played_questionadapter extends RecyclerView.Adapter<played_questionadapter.ViewHolder>{

    private ArrayList<Played_QUestion> questionlist;
    private Context context;

    public played_questionadapter(ArrayList<Played_QUestion> questionlist,Context context){
        this.context=context;
        this.questionlist=questionlist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accuracy_questionnum;
        public TextView accuracy_questions;
        public TextView accuracy_of_answered_coorectly;
        public TextView choices1_content;
        public TextView choices1_percent;
        public TextView choices2_content;
        public TextView choices2_percent;
        public TextView choices3_content;
        public TextView choices3_percent;
        public TextView choices4_content;
        public TextView choices4_percent;
        public TextView playeranswer;


        public ViewHolder(View v) {
            super(v);
            accuracy_questionnum = v.findViewById(R.id.accuracy_questionnum);
            accuracy_questions = v.findViewById(R.id.accuracy_questions);
            accuracy_of_answered_coorectly = v.findViewById(R.id.accurracy_of_ans_correctly);
            choices1_content = v.findViewById(R.id.choices1_content);
            choices2_content = v.findViewById(R.id.choices2_content);
            choices3_content = v.findViewById(R.id.choices3_content);
            choices4_content = v.findViewById(R.id.choices4_content);
            choices2_percent = v.findViewById(R.id.choices2_percent);
            choices1_percent = v.findViewById(R.id.choices1_percent);
            choices3_percent = v.findViewById(R.id.choices3_percent);
            choices4_percent = v.findViewById(R.id.choices4_percent);
            playeranswer=v.findViewById(R.id.playeranswered);
        }
    }

    @Override
    public int getItemCount() {
        return questionlist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Played_QUestion currentitem=questionlist.get(position);
        holder.accuracy_questionnum.setText("Question "+(position+1));
        holder.accuracy_questions.setText(currentitem.getQuestion());
        holder.accuracy_of_answered_coorectly.setText("");
        holder.choices1_content.setText(currentitem.getChoices().get(0).getChoice());
        holder.choices1_percent.setText(currentitem.getChoices().get(0).getAccuracy()+"%");
        holder.choices2_content.setText(currentitem.getChoices().get(1).getChoice());
        holder.choices2_percent.setText(currentitem.getChoices().get(1).getAccuracy()+"%");
        holder.choices3_content.setText(currentitem.getChoices().get(2).getChoice());
        holder.choices3_percent.setText(currentitem.getChoices().get(2).getAccuracy()+"%");
        holder.choices4_content.setText(currentitem.getChoices().get(3).getChoice());
        holder.choices4_percent.setText(currentitem.getChoices().get(3).getAccuracy()+"%");
        if(currentitem.getChoices().get(0).getIsCorrect()==true){
            holder.choices1_content.setTextColor(Color.parseColor("#16D46B"));
        }
        if(currentitem.getChoices().get(1).getIsCorrect()==true){
            holder.choices2_content.setTextColor(Color.parseColor("#16D46B"));
        }
        if(currentitem.getChoices().get(2).getIsCorrect()==true){
            holder.choices3_content.setTextColor(Color.parseColor("#16D46B"));
        }
        if(currentitem.getChoices().get(3).getIsCorrect()==true){
            holder.choices4_content.setTextColor(Color.parseColor("#16D46B"));
        }
        if(currentitem.getChoices().get(0).getIsAnswer()!=null){
           holder.playeranswer.setText(currentitem.getChoices().get(0).getChoice());
           if(currentitem.getChoices().get(0).getIsCorrect()==true){
               holder.playeranswer.setBackgroundColor(Color.parseColor("#16D46B"));
           }else{
               holder.playeranswer.setBackgroundColor(Color.parseColor("#E21A3C"));
           }
        }
        else if(currentitem.getChoices().get(1).getIsAnswer()!=null){
            holder.playeranswer.setText(currentitem.getChoices().get(1).getChoice());
            if(currentitem.getChoices().get(1).getIsCorrect()==true){
                holder.playeranswer.setBackgroundColor(Color.parseColor("#16D46B"));
            }else{
                holder.playeranswer.setBackgroundColor(Color.parseColor("#E21A3C"));
            }
        }
        else if(currentitem.getChoices().get(2).getIsAnswer()!=null){
            holder.playeranswer.setText(currentitem.getChoices().get(2).getChoice());
            if(currentitem.getChoices().get(2).getIsCorrect()==true){
                holder.playeranswer.setBackgroundColor(Color.parseColor("#16D46B"));
            }else{
                holder.playeranswer.setBackgroundColor(Color.parseColor("#E21A3C"));
            }
        }
        else if(currentitem.getChoices().get(3).getIsAnswer()!=null){
            holder.playeranswer.setText(currentitem.getChoices().get(3).getChoice());
            if(currentitem.getChoices().get(3).getIsCorrect()==true){
                holder.playeranswer.setBackgroundColor(Color.parseColor("#16D46B"));
            }else{
                holder.playeranswer.setBackgroundColor(Color.parseColor("#E21A3C"));
            }
        }
        else{
            holder.playeranswer.setText("You didnt answer this question");
            holder.playeranswer.setBackgroundColor(Color.parseColor("#6C757D"));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questioncardview,parent,false);
        return new played_questionadapter.ViewHolder(view);
    }
}
