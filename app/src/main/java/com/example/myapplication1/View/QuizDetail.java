package com.example.myapplication1.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Question;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;

import org.json.JSONArray;

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

public class QuizDetail extends AppCompatActivity {
private TextView listquestion;
private Button mcq,shortans;
public static final String QUIZID="quizid";
public static final String QUIZTYPE="quiztype";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        Intent intent=getIntent();
        String quiz_title=intent.getStringExtra(EXTRA_title);
        final String quiz_creator=intent.getStringExtra(EXTRA_creator);
        int quiz_totalplay=intent.getIntExtra(EXTRA_totalplay,0);
         final int position=intent.getIntExtra(EXTRA_POSITION,0);

        TextView title=(TextView)findViewById(R.id.qd_title);
        TextView creator=(TextView)findViewById(R.id.qd_creator);
        TextView totalplay=(TextView)findViewById(R.id.qd_totalplays);

        title.setText(quiz_title);
        creator.setText("Created By: "+quiz_creator);
        totalplay.setText(quiz_totalplay+" Questions");

        mcq=(Button) findViewById(R.id.mcq);
        shortans=(Button)findViewById(R.id.shortans);


        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://192.168.0.173:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        listquestion=(TextView)findViewById(R.id.qd_question) ;


        JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
        Call<List<Quizzes>> call=jsonPlaceHolderAPi.getQuizzes();
        call.enqueue(new Callback<List<Quizzes>>() {
            @Override
            public void onResponse(Call<List<Quizzes>> call, Response<List<Quizzes>> response)
            {
                final List<Quizzes> quizzes=response.body();
                int length=quizzes.get(position).getQuizQuestions().size();
                 for(int i = 0; i<length;i++){
                     int number=i+1;
                    String content="";
                  /*  for(int j=0;j<=;i++)
                    choices+=quizzes.get(position).getQuizQuestions().get(i).getChoices().get(0).getChoice()+;*/
                    content+=+number+"."+quizzes.get(position).getQuizQuestions().get(i).getQuestion()+"\n";

                    listquestion.append(content);

                }




                mcq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(QuizDetail.this,HostingGame.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("quizid",quizzes.get(position).getId());
                        bundle.putString("quiztype","ss");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
                public void onFailure(Call<List<Quizzes>> call, Throwable t) {
                    listquestion.setText(t.getMessage());
                }
        });

        mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
