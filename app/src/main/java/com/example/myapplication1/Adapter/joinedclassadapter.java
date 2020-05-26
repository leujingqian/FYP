package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class joinedclassadapter extends RecyclerView.Adapter<joinedclassadapter.ViewHolder> {
    private ArrayList<Classes> classeslist;
    private Context context;
    private joinedclassadapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(joinedclassadapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public joinedclassadapter(ArrayList<Classes>classeslist, Context context){
        this.context=context;
        this.classeslist=classeslist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView classname, session, adminname;
        public TextView threedots;

        public ViewHolder(View v) {
            super(v);
            classname = v.findViewById(R.id.joined_classname);
            session = v.findViewById(R.id.joined_session);
            adminname = v.findViewById(R.id.joined_admin);
            threedots=v.findViewById(R.id.joined_textViewOptions);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Classes currentItem=classeslist.get(position);
        holder.adminname.setText(currentItem.getAdmins().get(0).getName());
        holder.classname.setText(currentItem.getName());
        holder.session.setText(currentItem.getSection());
        holder.threedots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,holder.threedots);
                popupMenu.inflate(R.menu.joinedrv_option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch ((item.getItemId())) {
                            case R.id.unenrolled:
                            SharedPreferences sharedPreferencetoken =context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
                            String token = sharedPreferencetoken.getString("Token", null);
                            Retrofit retrofit =new Retrofit.Builder()
                                    .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            Classes classes=new Classes(currentItem.getClassId());
                            final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
                            Call<Classes> call=jsonPlaceHolderAPi.unerolled("Bearer "+token,classes);
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
                                        Toast.makeText(context,"Succesfully exit",Toast.LENGTH_SHORT).show();


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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_cardview,parent,false);
        return new joinedclassadapter.ViewHolder(view);
    }
}
