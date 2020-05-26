package com.example.myapplication1.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication1.Adapter.QuestionAdapter;
import com.example.myapplication1.Model.Choice;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Question;
import com.example.myapplication1.Model.QuestionList;
import com.example.myapplication1.Model.QuizQuestion;
import com.example.myapplication1.Model.Quizzes;
import com.example.myapplication1.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class createquiz extends AppCompatActivity implements QuestionAdapter.OnItemClickListener  {
    private RecyclerView mrecyclerview;
    private QuestionAdapter madapter;
    public ArrayList<QuestionList> question_list = new ArrayList<>();
    private Button createquestions,upload;
    private RecyclerView.LayoutManager mLayoutManager;
    public TextInputLayout quiztitle;

    public static final String EX_choices1="questionchoices1";
    public static final String EX_choices2="questionchoices2";
    public static final String EX_choices3="questionchoices3";
    public static final String EX_choices4="questionchoices4";
    public static final String EX_questions="question";
    public static final String EX_correctans="correctAns";
    public static final String EX_index="index";
    public static final String EX_timer="timer";
    public static final String EX_iscorrect1="correct1";
    public static final String EX_iscorrect2="correct2";
    public static final String EX_iscorrect3="correct3";
    public static final String EX_iscorrect4="correct4";
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquiz);
        quiztitle=findViewById(R.id.text_input_quiztitle);
        loadData();
        buildRecyclerView();
        madapter.setOnItemClickListerner(createquiz.this);
        createquestions = findViewById(R.id.bt_addquestion);
        createquestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createquiz.this, createquestion.class);
                createquiz.this.startActivity(intent);
            }
        });
        upload=findViewById(R.id.bt_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quiztitle.getEditText().getText().toString().trim().isEmpty()){
                    quiztitle.setError("Quiz title cannot be blank");
                }

                if(!quiztitle.getEditText().getText().toString().trim().isEmpty()) {
                    savequiz();
                    sharedPreferences.edit().clear().apply();
                    Intent intent = new Intent(createquiz.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
    }

    public void buildRecyclerView() {
        mrecyclerview = findViewById(R.id.question_card_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(mLayoutManager);
        mrecyclerview.setHasFixedSize(true);
        madapter = new QuestionAdapter(question_list, createquiz.this);
        mrecyclerview.setAdapter(madapter);
    }

    public void loadData() {
    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Question list", null);
        Type type = new TypeToken<ArrayList<QuestionList>>() {}.getType();
        question_list = gson.fromJson(json, type);

        if (question_list == null) {
            question_list = new ArrayList<>();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent question=new Intent(createquiz.this,createquestion.class);
        QuestionList clickedItem=question_list.get(position);
        question.putExtra(EX_choices1,clickedItem.getChoices1());
        question.putExtra(EX_choices2,clickedItem.getChoices2());
        question.putExtra(EX_choices3,clickedItem.getChoices3());
        question.putExtra(EX_choices4,clickedItem.getChoices4());
        question.putExtra(EX_questions,clickedItem.getQuestion());
        question.putExtra(EX_correctans,clickedItem.getCorrectAns());
        question.putExtra(EX_timer,clickedItem.getTimer());
        question.putExtra(EX_iscorrect1,clickedItem.isIscorrect1());
        question.putExtra(EX_iscorrect2,clickedItem.isIscorrect2());
        question.putExtra(EX_iscorrect3,clickedItem.isIscorrect3());
        question.putExtra(EX_iscorrect4,clickedItem.isIscorrect4());
        question.putExtra(EX_index,position);
        startActivity(question);
    }

    public void  savequiz(){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://collaborative-learning-system.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Question list", null);
        Type type = new TypeToken<ArrayList<QuestionList>>() {}.getType();
        question_list = gson.fromJson(json, type);

//    quizz.setTitle(quiztitle.getEditText().getText().toString().trim());


        List<QuizQuestion> question=new ArrayList<>();
        List<Choice> choice=new ArrayList<>();
        for(int i=0;i<question_list.size();i++){

            QuizQuestion ques=new QuizQuestion();
            Choice choi=new Choice();

                choi.setChoice(question_list.get(i).getChoices1());
                choi.setIsCorrect(question_list.get(i).isIscorrect1());
                choice.add(choi);
            Choice choi1=new Choice();
                choi1.setChoice(question_list.get(i).getChoices2());
                choi1.setIsCorrect(question_list.get(i).isIscorrect2());
                choice.add(choi1);
            Choice choi2=new Choice();
                choi2.setChoice(question_list.get(i).getChoices3());
                choi2.setIsCorrect(question_list.get(i).isIscorrect3());
                choice.add(choi2);
            Choice choi3=new Choice();
                choi3.setChoice(question_list.get(i).getChoices4());
                choi3.setIsCorrect(question_list.get(i).isIscorrect4());
                choice.add(choi3);

            ques.setQuestion(question_list.get(i).getQuestion());
            ques.setTimer(question_list.get(i).getTimer());



            ques.setChoices(choice);
            question.add(ques);
        }
        SharedPreferences sharedPreferencetoken=getSharedPreferences("userdata", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor=sharedPreferences.edit();
         String token=sharedPreferencetoken.getString("Token",null);
        Quizzes quizz=new Quizzes(quiztitle.getEditText().getText().toString().trim(),question);
        Call<Quizzes> call=jsonPlaceHolderAPi.callQuizzes("Bearer "+token,quizz);
        call.enqueue(new Callback<Quizzes>() {
            @Override
            public void onResponse(Call<Quizzes> call, Response<Quizzes> response) {
                Toast.makeText(createquiz.this,response.code(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Quizzes> call, Throwable t) {

            }
        });
        sharedPreferences.edit().clear().apply();
    }
    public void onBackPressed() {
        sharedPreferences.edit().clear().apply();
        finish();
        Intent intent = new Intent(createquiz.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
