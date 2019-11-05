package com.example.myapplication1.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.myapplication1.Model.Question;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private ArrayList<Question> question_list;
    private Context context;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListerner(OnItemClickListener listener){
        mlistener=listener;
    }
    public QuestionAdapter(ArrayList<Question> question_list, Context context) {
        this.context = context;
        this.question_list = question_list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_question;
        public TextInputLayout question;
        public TextInputLayout choices1;
        public TextInputLayout choices2;
        public TextInputLayout choices3;
        public TextInputLayout choices4;
        public RadioButton radio1;
        public RadioButton radio2;
        public RadioButton radio3;
        public RadioButton radio4;

        public ViewHolder(View v) {

            super(v);
            tv_question = v.findViewById(R.id.question);
            question=v.findViewById(R.id.text_input_question);
            choices1=v.findViewById(R.id.text_input_choices1);
            choices2=v.findViewById(R.id.text_input_choices2);
            choices3=v.findViewById(R.id.text_input_choices3);
            choices4=v.findViewById(R.id.text_input_choices4);
            radio1=v.findViewById(R.id.radio_one);
            radio2=v.findViewById(R.id.radio_two);
            radio3=v.findViewById(R.id.radio_three);
            radio4=v.findViewById(R.id.radio_four);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (question_list != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.OnItemClick(position);
                        }
                    }
                }
            });


        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Question currentItem=question_list.get(i);
        viewHolder.tv_question.setText(currentItem.getQuestion());
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_question, viewGroup, false);
        return new QuestionAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return question_list.size();
    }



        }

