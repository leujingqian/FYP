package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.myapplication1.Model.Reports;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class HostedReportAdapter extends RecyclerView.Adapter<HostedReportAdapter.ViewHolder> {

    private ArrayList<Reports> hostedreports;
    private Context context;
    private OnItemClickListener mListerner;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListerner = listener;
    }
    public HostedReportAdapter(ArrayList<Reports>hostedreports, Context context){
        this.context=context;
        this.hostedreports=hostedreports;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView report_title;
        public TextView report_date;
        public TextView report_player;
        public TextView hostername;

        public ViewHolder(View v){
            super(v);
            report_title=v.findViewById(R.id.reporttitle);
            report_date=v.findViewById(R.id.createddate);
            report_player=v.findViewById(R.id.playernum);
            hostername=v.findViewById(R.id.hostername);

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
        Reports currentItem=hostedreports.get(position);
        holder.report_title.setText(currentItem.getGame_name());
        holder.report_date.setText(currentItem.getHosted_date().replace('T',' '));
        holder.report_player.setText(currentItem.getPlayer_results().size()+" Plays");
        holder.hostername.setVisibility(View.INVISIBLE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_recyclerview,parent,false);
        return new HostedReportAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return hostedreports.size();
    }
}
