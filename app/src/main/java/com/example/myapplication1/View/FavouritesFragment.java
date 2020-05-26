package com.example.myapplication1.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Adapter.DataAdapter;
import com.example.myapplication1.Adapter.FavouriteAdapter;
import com.example.myapplication1.Model.Favourite;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FavouritesFragment extends Fragment implements FavouriteAdapter.OnItemClickListener {
public TextView textview;
    private RecyclerView mrecyclerView;
    private ArrayList<Favourite> favouritelist=new ArrayList<>();
    private FavouriteAdapter adapter;

    /*@Override
    public void onResume() {
        super.onResume();
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        if(Build.VERSION.SDK_INT>=26){
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_favourites, container, false);

        mrecyclerView = (RecyclerView) v.findViewById(R.id.fv_card_recycler_view);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setHasFixedSize(true);



        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
        SharedPreferences sharedPreferencetoken = getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String token = sharedPreferencetoken.getString("Token", null);
        Call<List<Favourite>> call1=jsonPlaceHolderAPi.getFavourites("Bearer "+token);
        call1.enqueue(new Callback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {

                    if (response.isSuccessful() && response.body()!=null) {

                        favouritelist = new ArrayList<>(response.body());
                        adapter=new FavouriteAdapter(favouritelist,getActivity());
                        mrecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(FavouritesFragment.this);
//                        textview.setVisibility(v.INVISIBLE);
                    }
                    if(response.body()==null){
                        Toast.makeText(getContext(),"No result",Toast.LENGTH_SHORT).show();
                    }



            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



       return v;
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
        Favourite clickeditem=favouritelist.get(position);
        detailIntent.putExtra("quizid",clickeditem.getQuizzes().getId());
        startActivity(detailIntent);
    }
}
