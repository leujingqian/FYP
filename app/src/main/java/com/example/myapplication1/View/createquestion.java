package com.example.myapplication1.View;

import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.myapplication1.Adapter.QuestionAdapter;
import com.example.myapplication1.Model.Question;
import com.example.myapplication1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class createquestion extends AppCompatActivity {
      private Button buttonsave;
      private String question,choices1,choices2,choices3,choices4,correctAns;
ArrayList<Question> question_list=new ArrayList<>();
private QuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion);
        adapter = new QuestionAdapter(question_list,this);




        buttonsave=findViewById(R.id.bt_save);
            buttonsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextInputLayout ti_question=(TextInputLayout)findViewById(R.id.text_input_question);
                    TextInputLayout ti_choices1=(TextInputLayout)findViewById(R.id.text_input_choices1);
                    TextInputLayout ti_choices2=(TextInputLayout)findViewById(R.id.text_input_choices2);
                    TextInputLayout ti_choices3=(TextInputLayout)findViewById(R.id.text_input_choices3);
                    TextInputLayout ti_choices4=(TextInputLayout)findViewById(R.id.text_input_choices4);
                    question=ti_question.getEditText().getText().toString().trim();
                    choices1=ti_choices1.getEditText().getText().toString().trim();
                    choices2=ti_choices2.getEditText().getText().toString().trim();
                    choices3=ti_choices3.getEditText().getText().toString().trim();
                    choices4=ti_choices4.getEditText().getText().toString().trim();

                    RadioButton cA_A=findViewById(R.id.radio_one);
                    RadioButton cA_B=findViewById(R.id.radio_two);
                    RadioButton cA_C=findViewById(R.id.radio_three);
                    RadioButton cA_D=findViewById(R.id.radio_four);

                    if(cA_A.isChecked()) {
                        correctAns = "1";
                    }
                     if(cA_B.isChecked())
                    {
                        correctAns="2";
                    }
                   if(cA_C.isChecked())
                    {
                        correctAns="3";
                    }
                    if (cA_D.isChecked()){
                        correctAns="4";
                    }

                    insertitem(question,choices1,choices2,choices3,choices4,correctAns);
                    saveData();

                    Intent intent = new Intent(createquestion.this, createquiz.class);
                    createquestion.this.startActivity(intent);

                }
            });

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(question_list);
        editor.putString("Question list", json);
        editor.apply();
    }

    private void insertitem(String question,String choices1, String choices2, String choices3, String choices4,String correctAns){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Question list", null);
        Type type = new TypeToken<ArrayList<Question>>() {}.getType();
        question_list = gson.fromJson(json, type);
        if (question_list == null) {
            question_list = new ArrayList<>();
        }

//        question_list.add(new Question(question,choices1,choices2,choices3,choices4,correctAns));
        adapter.notifyItemChanged(question_list.size());

    }
}
