package com.example.myapplication1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Favourite;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private ArrayList<Favourite> favouritelist;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FavouriteAdapter(ArrayList<Favourite>favouritelist,Context context){
        this.context=context;
        this.favouritelist=favouritelist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title;
        public TextView tv_creator;
        public TextView tv_totalplays;
        public MaterialCardView cardView;

        public ViewHolder(View v){
            super(v);
            tv_title=v.findViewById(R.id.fv_quiztitle);
            tv_creator=v.findViewById(R.id.fv_creator);
            tv_totalplays=v.findViewById(R.id.fv_questionnum);
            cardView=v.findViewById(R.id.card_view);
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
        Favourite currentItem=favouritelist.get(position);
        holder.tv_title.setText(currentItem.getQuizzes().getTitle());
        holder.tv_creator.setText(currentItem.getQuizzes().getCreator().getName());
        holder.cardView.setStrokeColor(Color.parseColor("#325ea8"));
       holder.tv_totalplays.setText(" "+currentItem.getQuizzes().getQuestions().size()+" Questions");//Number of Question
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.expcardview,parent,false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return favouritelist.size();
    }
}

