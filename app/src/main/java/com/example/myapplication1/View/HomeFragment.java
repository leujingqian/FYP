package com.example.myapplication1.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication1.Adapter.DataAdapter;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements DataAdapter.OnItemClickListener  {
    private RecyclerView mrecyclerView;
    private ArrayList<Quizzes> quizzlist=new ArrayList<>();
    private DataAdapter adapter;
    public static final String EXTRA_title="quiz_title";
    public static final String EXTRA_creator="quiz_creator";
    public static final String EXTRA_totalplay="quiz_totalplay";
    public static final String EXTRA_POSITION="position";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        mrecyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView.setHasFixedSize(true);


        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://192.168.0.173:3000/")
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
                }
            }

            @Override
            public void onFailure(Call<List<Quizzes>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



       /* call1.enqueue(new  Call<List<Quizzes>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<Quizzes>> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    quizzlist = new ArrayList<>(response.body());
                    adapter=new DataAdapter(quizzlist,getActivity());
                    mrecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Quizzes>> call, Throwable t) {
                Toast.makeText(getActivity(),"Oops! Something went wrong!",Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;


    }
    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(getActivity(),QuizDetail.class);
        Quizzes clickedItem=quizzlist.get(position);
        detailIntent.putExtra(EXTRA_title,clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_creator,clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_totalplay,clickedItem.getTotalPlays());
        detailIntent.putExtra(EXTRA_POSITION,position);
        startActivity(detailIntent);
    }
}
