package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Member;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;
import com.example.myapplication1.View.CreateClass;
import com.example.myapplication1.View.QuizDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class createdclassadapter extends RecyclerView.Adapter<createdclassadapter.ViewHolder> {
    private ArrayList<Classes> classeslist;
    private Context context;
    public static final String Extraname="classname";
    public static final String Extrasession="session";
    public static final String Extratutorial="tutorial";
    public static final String Extraclassid="classid";
    public static final String extravalue="value";
    private createdclassadapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(createdclassadapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public createdclassadapter(ArrayList<Classes>classeslist, Context context){
        this.context=context;
        this.classeslist=classeslist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_classsname;
        public TextView tv_session;
        public TextView tv_studentnum;
        public TextView threedots;
        public TextView tv_classid;

        public ViewHolder(View v){
            super(v);
            tv_classsname=v.findViewById(R.id.tv_classname);
            tv_session=v.findViewById(R.id.tv_session);
            tv_studentnum=v.findViewById(R.id.tv_studentnum);
            threedots=v.findViewById(R.id.textViewOptions);
            tv_classid=v.findViewById(R.id.classid);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            Classes clickeditem= classeslist.get(position);
                            String classid=clickeditem.getClassId();
                            Intent intent=new Intent("memberid");
                            intent.putExtra("id",classid);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Classes currentItem=classeslist.get(position);
        holder.tv_studentnum.setText(currentItem.getMembers().size()+" Students");
        holder.tv_classsname.setText(currentItem.getName());
        holder.tv_session.setText(currentItem.getSection());
        holder.tv_classid.setText(currentItem.getClassId());
        holder.threedots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.threedots);
                popupMenu.inflate(R.menu.rv_option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch ((item.getItemId())) {
                            case R.id.edit:
                                Intent intent = new Intent(context, CreateClass.class);
                                intent.putExtra(Extraname,currentItem.getName());
                                intent.putExtra(Extrasession,currentItem.getSection());
                                intent.putExtra(Extratutorial,currentItem.getTutorialGroup());
                                intent.putExtra(Extraclassid,currentItem.getId());
                                intent.putExtra(extravalue,position);
                                context.startActivity(intent);
                              /*  Intent detailIntent=new Intent(getActivity(), QuizDetail.class);
                                Quizzes clickedItem=quizzlist.get(position);
                                detailIntent.putExtra(EXTRA_title,clickedItem.getTitle());
                                detailIntent.putExtra(EXTRA_creator,clickedItem.getCreator().getName());
                                detailIntent.putExtra(EXTRA_totalplay,clickedItem.getPlays());
                                detailIntent.putExtra(EXTRA_POSITION,position);
                                startActivity(detailIntent);*/
                                return true;
                            case R.id.delete:
                                SharedPreferences sharedPreferencetoken =context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
                                String token = sharedPreferencetoken.getString("Token", null);
                                Retrofit retrofit =new Retrofit.Builder()
                                        .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
                                Call<Classes> call=jsonPlaceHolderAPi.deleteclasses("Bearer "+token,currentItem.getId());
                                call.enqueue(new Callback<Classes>() {
                                    @Override
                                    public void onResponse(Call<Classes> call, Response<Classes> response) {
                                        if(response.code()>=400){
                                            if(!response.isSuccessful()) {
                                                JSONObject jsonobject=null;
                                                try{
                                                    try {
                                                        jsonobject=new JSONObject(response.errorBody().string());
                                                    }catch (IOException e){
                                                        e.printStackTrace();
                                                    }


                                                    String message=jsonobject.getString("err");
                                                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();


                                                }catch (JSONException e){
                                                    e.printStackTrace();
                                                }

                                            }
                                        }else{
                                            Toast.makeText(context,"Succesfully delete",Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Classes> call, Throwable t) {

                                    }
                                });
                                return true;
                                default:
                                    return false;
                        }
                    }

                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classeslist.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.created_cardview,parent,false);
        return new createdclassadapter.ViewHolder(view);
    }
}
