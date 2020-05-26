package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.Member;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.Model.Reports;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class memberlistadapter extends RecyclerView.Adapter<memberlistadapter.ViewHolder>{

    private ArrayList<Member> memberlist;
    private Context context;
    private memberlistadapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int posiition);
    }
    public void setOnItemClickListener(memberlistadapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public memberlistadapter(ArrayList<Member>memberlist, Context context){
            this.context=context;
            this.memberlist=memberlist;
    }


        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView studentname;
            public ImageView deletemember;

            public ViewHolder(View v){
                super(v);
                studentname=v.findViewById(R.id.membername);
                deletemember=v.findViewById(R.id.trash);
                deletemember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick(position);
                                Member clickeditem= memberlist.get(position);
                                String memberid=clickeditem.getId();
                                Intent intent=new Intent("memberid");
                                intent.putExtra("id",memberid);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            }
                        }
                    }
                });
            }
        }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member currentItem=memberlist.get(position);
        holder.studentname.setText(currentItem.getName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlist_cardview,parent,false);

        return new memberlistadapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return memberlist.size();
    }
}

