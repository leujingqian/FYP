package com.example.myapplication1.View;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication1.Adapter.DataAdapter;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.Model.notification_model;
import com.example.myapplication1.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication1.Model.App.CHANNEL_1_ID;

public class HomeFragment extends Fragment implements DataAdapter.OnItemClickListener  {
    private RecyclerView mrecyclerView;
    private ArrayList<Quizzes> quizzlist=new ArrayList<>();
    private DataAdapter adapter;
    private Socket socket;
    private ProgressBar progressBar;

    public static final String EXTRA_title="quiz_title";
    public static final String EXTRA_creator="quiz_creator";
    public static final String EXTRA_totalplay="quiz_totalplay";
    public static final String EXTRA_POSITION="position";
    private NotificationManagerCompat notificationManagerCompat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView.setHasFixedSize(true);
        notificationManagerCompat=NotificationManagerCompat.from(getActivity());
        progressBar=(ProgressBar)view.findViewById(R.id.progressbar);
        Sprite cubeGrid = new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);



        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
        Call<List<Quizzes>> call1=jsonPlaceHolderAPi.getQuizzes();
        call1.enqueue(new Callback<List<Quizzes>>() {
            @Override
            public void onResponse(Call<List<Quizzes>> call, Response<List<Quizzes>> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    quizzlist = new ArrayList<>(response.body());
                    adapter=new DataAdapter(quizzlist,getActivity());
                    mrecyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(HomeFragment.this);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Quizzes>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String token = sharedPreferencetoken.getString("Token", null);
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.query = "auth_token=" + token;
//             Socket socket = IO.socket("https://collaborative-learning-system.herokuapp.com/", opts);
            socket = IO.socket("https://collaborative-learning-system.herokuapp.com/notification", opts);
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        socket.on("new-notification", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final String response=(String)args[0] ;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Notification notification=new NotificationCompat.Builder(getActivity(),CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle("CLS Learning")
                                .setContentText(response)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManagerCompat.notify(1,notification);
                    }
                });
            }
        });
        final List<notification_model>lisdata=new ArrayList<>();
        socket.on("total-notifications", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                final int notification_num=(Integer)args[0];
            }
        });

        final ImageButton notificationbutton=view.findViewById(R.id.notification);
        notificationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("read-notification", new Ack() {
                    @Override
                    public void call(Object... args) {
                        JSONArray array=(JSONArray) args[0];
                        for(int i=0;i<array.length();i++){
                            try{
                                array.getJSONObject(i).getString("content");
                                notification_model notification_model=new notification_model(array.getJSONObject(i).getString("content"),array.getJSONObject(i).getString("time_stamp"));
                                lisdata.add(notification_model);
                                Toast.makeText(getActivity(),lisdata.get(i).toString().trim(),Toast.LENGTH_SHORT).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                            Gson gson=new Gson();
                            String notf=gson.toJson(lisdata);
                            Intent intent=new Intent(getActivity(), com.example.myapplication1.View.notification.class);
                            intent.putExtra("listnotification",notf);
                            startActivity(intent);


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
            }
        });






        return view;


    }
    @Override
    public void onItemClick(int position) {
       /* Intent detailIntent=new Intent(getActivity(),QuizDetail.class);
        Quizzes clickedItem=quizzlist.get(position);
        detailIntent.putExtra(EXTRA_title,clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_creator,clickedItem.getCreator().getName());
        detailIntent.putExtra(EXTRA_totalplay,clickedItem.getPlays());
        detailIntent.putExtra(EXTRA_POSITION,position);
        startActivity(detailIntent);*/
        Intent detailIntent=new Intent(getActivity(),QuizDetail.class);
        Quizzes clickedItem=quizzlist.get(position);
        detailIntent.putExtra("quizid",clickedItem.getId());
        startActivity(detailIntent);
    }
    public void sendOnChannel1(){
        Notification notification=new NotificationCompat.Builder(getActivity(),CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("CLS Learning")
                .setContentText("jajajjaja")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1,notification);
    }
}
