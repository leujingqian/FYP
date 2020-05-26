package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Member;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class joined_memberlistadapter extends RecyclerView.Adapter<joined_memberlistadapter.ViewHolder> {
    private ArrayList<Member> memberlist;
    private Context context;
    private joined_memberlistadapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int posiition);
    }
    public void setOnItemClickListener(joined_memberlistadapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public joined_memberlistadapter(ArrayList<Member>memberlist, Context context){
        this.context=context;
        this.memberlist=memberlist;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView studentname;

        public ViewHolder(View v){
            super(v);
            studentname=v.findViewById(R.id.joinedcv_membername);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull joined_memberlistadapter.ViewHolder holder, int position) {
        Member currentItem=memberlist.get(position);
        holder.studentname.setText(currentItem.getName());
    }

    @NonNull
    @Override
    public joined_memberlistadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joinmemberlist_cardview,parent,false);

        return new joined_memberlistadapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return memberlist.size();
    }
}
