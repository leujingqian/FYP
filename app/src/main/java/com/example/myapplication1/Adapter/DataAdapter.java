package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Quizzes>quizzlist;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public DataAdapter(ArrayList<Quizzes>quizzlist,Context context){
        this.context=context;
        this.quizzlist=quizzlist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title;
        public TextView tv_creator;
        public TextView tv_totalplays;

        public ViewHolder(View v){
            super(v);
            tv_title=v.findViewById(R.id.quiztitle);
            tv_creator=v.findViewById(R.id.creator);
            tv_totalplays=v.findViewById(R.id.questionnum);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quizzes currentItem=quizzlist.get(position);
        holder.tv_title.setText(currentItem.getTitle());
        holder.tv_creator.setText(currentItem.getCreator());
        holder.tv_totalplays.setText(" "+currentItem.getQuizQuestions().size()+" Questions");//Number of Question
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return quizzlist.size();
    }
}

