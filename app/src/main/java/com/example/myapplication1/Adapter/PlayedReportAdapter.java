package com.example.myapplication1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.PlayedReport;
import com.example.myapplication1.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class PlayedReportAdapter extends RecyclerView.Adapter<PlayedReportAdapter.ViewHolder> {

    private ArrayList<PlayedReport> playedreports;
    private Context context;
    private OnItemClickListener mListerner;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListerner = listener;
    }
    public PlayedReportAdapter(ArrayList<PlayedReport>playedReports,Context context){
        this.context=context;
        this.playedreports=playedReports;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView report_title;
        public TextView report_date;
        public TextView report_player;
        public TextView hostername;
        public MaterialCardView cardView;


        public ViewHolder(View v){
            super(v);
            report_title=v.findViewById(R.id.reporttitle);
            report_date=v.findViewById(R.id.createddate);
            report_player=v.findViewById(R.id.playernum);
            hostername=v.findViewById(R.id.hostername);
            cardView=v.findViewById(R.id.card_view);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListerner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListerner.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayedReport curretitem=playedreports.get(position);
        holder.cardView.setStrokeColor(Color.parseColor("#ff7142"));
        holder.report_date.setText(curretitem.getPlayedDate());
        holder.report_title.setText(curretitem.getGameName());
        holder.report_player.setVisibility(View.INVISIBLE);
//        holder.itemView.setBackgroundColor(Color.parseColor("#ff7142"));
        holder.hostername.setText("Hosted By :"+curretitem.getHosterName());
    }

    @Override
    public int getItemCount() {
        return playedreports.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_recyclerview,parent,false);
        return new PlayedReportAdapter.ViewHolder(view);
    }
}
