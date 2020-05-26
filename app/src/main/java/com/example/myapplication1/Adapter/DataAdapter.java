package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;

import java.util.ArrayList;

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
        public RelativeLayout relativeLayout;

        public ViewHolder(View v){
            super(v);
            tv_title=v.findViewById(R.id.quiztitle);
            tv_creator=v.findViewById(R.id.creator);
            tv_totalplays=v.findViewById(R.id.questionnum);
            relativeLayout=v.findViewById(R.id.rl1);



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
        holder.tv_creator.setText(currentItem.getCreator().getName());
        holder.tv_totalplays.setText(" "+currentItem.getQuestions().size()+" Questions");//Number of Question
        if(position %2 == 1)
        {
            holder.relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.triangles_red));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
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

