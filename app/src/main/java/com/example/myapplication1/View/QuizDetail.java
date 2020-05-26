package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Adapter.showquestionadapter;
import com.example.myapplication1.Model.Favourite;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Question;
import com.example.myapplication1.Model.QuizQuestion;
import com.example.myapplication1.Model.Quizid;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication1.View.HomeFragment.EXTRA_POSITION;
import static com.example.myapplication1.View.HomeFragment.EXTRA_creator;
import static com.example.myapplication1.View.HomeFragment.EXTRA_title;
import static com.example.myapplication1.View.HomeFragment.EXTRA_totalplay;
//implements SharedDialog.ExampleDialogListener
public class QuizDetail extends AppCompatActivity implements SharedDialog.ExampleDialogListener {
private TextView listquestion;
public Button mcq;
private ImageButton favourite,share;
public boolean booleanvalue;
private String quizID;
    private RecyclerView mrecyclerView;
    private ArrayList<QuizQuestion> questionlist=new ArrayList<>();
    public  String favouritelist;
    private ArrayList<String> listfavourites=new ArrayList<String>();
    private ArrayList<String> listf=new ArrayList<String>();
    private showquestionadapter adapter;
    private String[]stringlist;
public static final String QUIZTYPE="quiztype";
    private LinearLayout linearLayout3;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview_quizdetail);

        mrecyclerView = (RecyclerView) findViewById(R.id.showquestion_recycler_view);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setHasFixedSize(true);

        Intent intent=getIntent();
        quizID=intent.getStringExtra("quizid");

        final TextView title=(TextView)findViewById(R.id.qd_title);
        final TextView creator=(TextView)findViewById(R.id.qd_creator);
        final TextView totalplay=(TextView)findViewById(R.id.qd_totalplays);
        favourite =findViewById(R.id.favoutitebutton);
        share=findViewById(R.id.sharebutton);
        mcq=(Button) findViewById(R.id.mcq);
        linearLayout3=findViewById(R.id.linearLayout3);
        progressBar=findViewById(R.id.spin_kit);
        Sprite wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);
        linearLayout3.setVisibility(View.GONE);
        getFavouriteList();
        SharedPreferences sharedPreferencetoken =getSharedPreferences("userdata", Context.MODE_PRIVATE);
        favouritelist=sharedPreferencetoken.getString("favourite",null);
        if(favouritelist!=null) {
            stringlist = favouritelist.split(",");
            Toast.makeText(getApplicationContext(),stringlist.length+"1223",Toast.LENGTH_SHORT).show();
        }


        if(checkquizid()==1){
            favourite.setImageResource(R.drawable.ic_star_yellow);
        }else{
            favourite.setImageResource(R.drawable.ic_star_black_24dp);
        }
        if(checkquizid()==1){
            favourite.setImageResource(R.drawable.ic_star_yellow);
        }else{
            favourite.setImageResource(R.drawable.ic_star_black_24dp);
        }
        //shared preference to get token
        String token = sharedPreferencetoken.getString("Token", null);

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
        final Call<Quizid> call=jsonPlaceHolderAPi.getQUizzesid("Bearer " + token,quizID);
        call.enqueue(new Callback<Quizid>() {
            @Override
            public void onResponse(Call<Quizid> call, Response<Quizid>response)
            {
                questionlist=new ArrayList<>(response.body().getQuiz().getQuestions());
                adapter=new showquestionadapter(questionlist,getApplicationContext());
                mrecyclerView.setAdapter(adapter);

                final Quizid quizzes=response.body();
                linearLayout3.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                title.setText(quizzes.getQuiz().getTitle());
                creator.setText("Created By: "+quizzes.getQuiz().getCreator().getName());
                totalplay.setText(quizzes.getQuiz().getPlays()+" Plays");


                mcq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SettingDialog settingDialog=new SettingDialog();

                        Bundle bundle=new Bundle();
                        bundle.putString("quizid",quizID);
                        settingDialog.setArguments(bundle);
                        settingDialog.show(getSupportFragmentManager(),"Setting Dialog");
                    }
                });
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkquizid()==0) {
                            addfavourite(quizID);
                            List<String> list = new ArrayList<String>(Arrays.asList(stringlist));
                            list.add(quizID);
                            stringlist = list.toArray(new String[0]);
                            return;

                        }else{
                            unfavourite(quizID);
                            List<String> list = new ArrayList<String>(Arrays.asList(stringlist));
                            list.remove(quizID);
                            stringlist = list.toArray(new String[0]);
                            return;
                        }
                    }
                });

            }

            @Override
                public void onFailure(Call<Quizid> call, Throwable t) {
                    listquestion.setText(t.getMessage());
                }
        });






        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedDialog sharedDialog=new SharedDialog();
                Bundle bundle=new Bundle();
                bundle.putString("quizid",quizID);
                sharedDialog.setArguments(bundle);
                sharedDialog.show(getSupportFragmentManager(),"Shared Dialog");
            }
        });

    }
    public void applyTexts(Boolean isshared,String errormessage) {
        if(isshared==true){
            Toast.makeText(QuizDetail.this,errormessage,Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(QuizDetail.this,errormessage,Toast.LENGTH_SHORT).show();
    }
   private  void getFavouriteList(){
       final SharedPreferences sharedPreferencetoken =getSharedPreferences("userdata", Context.MODE_PRIVATE);
       String token = sharedPreferencetoken.getString("Token", null);
       SharedPreferences sharedPreferencefavourite =getSharedPreferences("userdata", Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor = sharedPreferencefavourite.edit();
       Retrofit retrofit =new Retrofit.Builder()
               .baseUrl("https://collaborative-learning-system.herokuapp.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
       Call <List<Favourite>> call2=jsonPlaceHolderAPi.getFavourites("Bearer "+token);
       call2.enqueue(new Callback<List<Favourite>>() {
           @Override
           public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
               StringBuilder sb=new StringBuilder();
               for(int i=0;i<response.body().size();i++) {
                   sb.append(response.body().get(i).getQuizzes().getId()).append(",");

               }
               editor.putString("favourite",sb.toString());
               editor.apply();

//               Toast.makeText(getApplicationContext(),stringlist.length+"1223",Toast.LENGTH_SHORT).show();
              /*listfavourites=new ArrayList<String>(favouritelist);
//               Toast.makeText(getApplicationContext(),listfavourites.size()+"1223",Toast.LENGTH_SHORT).show();*/
           }

           @Override
           public void onFailure(Call<List<Favourite>> call, Throwable t) {

           }
       });
       /*listf=new ArrayList<String>(listfavourites);
  Toast.makeText(getApplicationContext(),this.listfavourites.size()+"1223",Toast.LENGTH_SHORT).show();
       return favouritelist;*/
       favouritelist=sharedPreferencetoken.getString("favourite",null);
       if(favouritelist!=null) {
           stringlist = favouritelist.split(",");
           Toast.makeText(getApplicationContext(),stringlist.length+"1223",Toast.LENGTH_SHORT).show();
       }

   }

   private void addfavourite(String quizID){
       final Favourite favourites = new Favourite(quizID);
       SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
       Retrofit retrofit =new Retrofit.Builder()
               .baseUrl("https://collaborative-learning-system.herokuapp.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
       String token = sharedPreferencetoken.getString("Token", null);
       Call<Favourite> call1 = jsonPlaceHolderAPi.postfavourite("Bearer " + token, favourites);
       call1.enqueue(new Callback<Favourite>() {
           @Override
           public void onResponse(Call<Favourite> call, Response<Favourite> response) {
               if (response.body().getFavorited() == true) {
                   Toast.makeText(QuizDetail.this, "Favourite Successfull", Toast.LENGTH_SHORT).show();
                   favourite.setImageResource(R.drawable.ic_star_yellow);




               } else
                   Toast.makeText(QuizDetail.this, "Favourite Fail", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<Favourite> call, Throwable t) {

           }
       });
   }

   private void unfavourite(String quizID){
       SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
       String token = sharedPreferencetoken.getString("Token", null);
       Retrofit retrofit =new Retrofit.Builder()
               .baseUrl("https://collaborative-learning-system.herokuapp.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
       Call<Favourite> call3 = jsonPlaceHolderAPi.unfavourite("Bearer " + token, quizID);
       call3.enqueue(new Callback<Favourite>() {
           @Override
           public void onResponse(Call<Favourite> call, Response<Favourite> response) {
               if(response.body().getDeleted()==true){
                   Toast.makeText(QuizDetail.this, "Unfavourite Successfull", Toast.LENGTH_SHORT).show();
                   favourite.setImageResource(R.drawable.ic_star_black_24dp);

               }else{
                   Toast.makeText(QuizDetail.this, "Unfavourite Fail", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<Favourite> call, Throwable t) {

           }
       });
   }
   private int checkquizid(){
        int j=0;
        if(favouritelist!=null){
        for(int i=0;i<stringlist.length;i++) {
            if (stringlist[i].equals(quizID)) {
                j = 1;
            }
        }
        }return  j;
    }

    /*@Override
    public void onBackPressed() {
        SharedPreferences sharedPreferencetoken = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencetoken.edit();
        editor.putString("favourite","");
        editor.apply();
        finish();
    }*/
}
