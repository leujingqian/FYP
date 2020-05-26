package com.example.myapplication1.View;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.myapplication1.Model.QuestionList;
import com.example.myapplication1.Model.QuizQuestion;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication1.Adapter.QuestionAdapter;
import com.example.myapplication1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.myapplication1.View.createquiz.EX_choices2;
import static com.example.myapplication1.View.createquiz.EX_choices3;
import static com.example.myapplication1.View.createquiz.EX_choices4;
import static com.example.myapplication1.View.createquiz.EX_correctans;
import static com.example.myapplication1.View.createquiz.EX_index;
import static com.example.myapplication1.View.createquiz.EX_iscorrect1;
import static com.example.myapplication1.View.createquiz.EX_iscorrect2;
import static com.example.myapplication1.View.createquiz.EX_iscorrect3;
import static com.example.myapplication1.View.createquiz.EX_iscorrect4;
import static com.example.myapplication1.View.createquiz.EX_questions;
import static com.example.myapplication1.View.createquiz.EX_choices1;
import static com.example.myapplication1.View.createquiz.EX_timer;

public class createquestion extends AppCompatActivity {
    private Button buttonsave;
    private String question, choices1, choices2, choices3, choices4, correctAns;
    private int time;
    ArrayList<QuestionList> question_list = new ArrayList<>();
    private QuestionAdapter adapter;
    public TextInputLayout ti_question, ti_choices1, ti_choices2, ti_choices3, ti_choices4, ti_timer;
    public CheckBox cA_A, cA_B, cA_C, cA_D;
    public boolean iscorrect1, iscorrect2, iscorrect3, iscorrect4, atleastOneisChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquestion);
        adapter = new QuestionAdapter(question_list, this);


        Intent intent = getIntent();
        String c1 = intent.getStringExtra(EX_choices1);
        String c2 = intent.getStringExtra(EX_choices2);
        String c3 = intent.getStringExtra(EX_choices3);
        String c4 = intent.getStringExtra(EX_choices4);
        String ques = intent.getStringExtra(EX_questions);
        int index = intent.getIntExtra(EX_index, -1);
        boolean Iscorrect1 = intent.getBooleanExtra(EX_iscorrect1, true);
        boolean Iscorrect2 = intent.getBooleanExtra(EX_iscorrect2, true);
        boolean Iscorrect3 = intent.getBooleanExtra(EX_iscorrect3, true);
        boolean Iscorrect4 = intent.getBooleanExtra(EX_iscorrect4, true);
        int gettimer = intent.getIntExtra(EX_timer, 0);

        ti_question = findViewById(R.id.text_input_question);
        ti_choices1 = findViewById(R.id.text_input_choices1);
        ti_choices2 = findViewById(R.id.text_input_choices2);
        ti_choices3 = findViewById(R.id.text_input_choices3);
        ti_choices4 = findViewById(R.id.text_input_choices4);
        ti_timer = findViewById(R.id.question_timer);
        cA_A = findViewById(R.id.checkBox_one);
        cA_B = findViewById(R.id.checkBox_two);
        cA_C = findViewById(R.id.checkBox_three);
        cA_D = findViewById(R.id.checkBox_four);
        buttonsave = findViewById(R.id.bt_save);

        if (index >= 0) {
            retrievedata(index, ques, c1, c2, c3, c4, Iscorrect1, Iscorrect2, Iscorrect3, Iscorrect4, gettimer);
        } else {
            saveQuestion();
        }


    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(question_list);
        editor.putString("Question list", json);
        editor.apply();
    }

    private void insertitem(String question, String choices1, String choices2, String choices3, String choices4, Boolean iscorrect1, Boolean iscorrect2, Boolean iscorrect3, Boolean iscorrect4, int timer) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Question list", null);
        Type type = new TypeToken<ArrayList<QuestionList>>() {
        }.getType();
        question_list = gson.fromJson(json, type);
        if (question_list == null) {
            question_list = new ArrayList<>();
        }

        question_list.add(new QuestionList(question, choices1, choices2, choices3, choices4, iscorrect1, iscorrect2, iscorrect3, iscorrect4, timer));
        adapter.notifyItemChanged(question_list.size());

    }

    private void retrievedata(final int position, String question, String choices1, String choices2, String choices3, String choices4, Boolean Iscorrect1, Boolean Iscorrect2, Boolean Iscorrect3, Boolean Iscorrect4, int rettimer) {
        ti_question.getEditText().setText(question);
        ti_choices1.getEditText().setText(choices1);
        ti_choices2.getEditText().setText(choices2);
        ti_choices3.getEditText().setText(choices3);
        ti_choices4.getEditText().setText(choices4);
        ti_timer.getEditText().setText(rettimer + "");
        if (Iscorrect1 == true) {
            cA_A.setChecked(true);
        } else {
            cA_A.setChecked(false);
        }
        if (Iscorrect2 == true) {
            cA_B.setChecked(true);
        } else {
            cA_B.setChecked(false);
        }
        if (Iscorrect3 == true) {
            cA_C.setChecked(true);
        } else {
            cA_C.setChecked(false);
        }
        if (Iscorrect4 == true) {
            cA_D.setChecked(true);
        } else {
            cA_D.setChecked(false);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Question list", null);
        Type type = new TypeToken<ArrayList<QuestionList>>() {
        }.getType();
        question_list = gson.fromJson(json, type);
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    question_list.get(position).setQuestion(ti_question.getEditText().getText().toString().trim());
                    question_list.get(position).setChoices1(ti_choices1.getEditText().getText().toString().trim());
                    question_list.get(position).setChoices2(ti_choices2.getEditText().getText().toString().trim());
                    question_list.get(position).setChoices3(ti_choices3.getEditText().getText().toString().trim());
                    question_list.get(position).setChoices4(ti_choices4.getEditText().getText().toString().trim());
                    question_list.get(position).setTimer(Integer.parseInt(ti_timer.getEditText().getText().toString().trim()));

                    if (cA_A.isChecked()) {
                        question_list.get(position).setIscorrect1(true);
                        atleastOneisChecked = true;
                    } else
                        question_list.get(position).setIscorrect1(false);

                    if (cA_B.isChecked()) {
                        question_list.get(position).setIscorrect2(true);
                        atleastOneisChecked = true;
                    } else
                        question_list.get(position).setIscorrect2(false);

                    if (cA_C.isChecked()) {
                        question_list.get(position).setIscorrect3(true);
                        atleastOneisChecked = true;

                    } else
                        question_list.get(position).setIscorrect3(false);

                    if (cA_D.isChecked()) {
                        question_list.get(position).setIscorrect4(true);
                        atleastOneisChecked = true;
                    } else
                        question_list.get(position).setIscorrect4(false);


                       validation();
                    if (!ti_question.getEditText().getText().toString().trim().isEmpty() && !ti_choices1.getEditText().getText().toString().trim().isEmpty() && !ti_choices2.getEditText().getText().toString().trim().isEmpty() && !ti_choices3.getEditText().getText().toString().trim().isEmpty() && !ti_choices4.getEditText().getText().toString().trim().isEmpty() && !ti_timer.getEditText().getText().toString().trim().isEmpty() && atleastOneisChecked == true) {

                    saveData();
                    adapter.notifyItemChanged(position);
                    Intent intent = new Intent(createquestion.this, createquiz.class);
                    createquestion.this.startActivity(intent);
                }
            }
        });


    }

    public void saveQuestion() {
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question = ti_question.getEditText().getText().toString().trim();
                choices1 = ti_choices1.getEditText().getText().toString().trim();
                choices2 = ti_choices2.getEditText().getText().toString().trim();
                choices3 = ti_choices3.getEditText().getText().toString().trim();
                choices4 = ti_choices4.getEditText().getText().toString().trim();


                if (cA_A.isChecked()) {
                    iscorrect1 = true;
                    atleastOneisChecked = true;
                } else
                    iscorrect1 = false;

                if (cA_B.isChecked()) {
                    iscorrect2 = true;
                    atleastOneisChecked = true;
                } else
                    iscorrect2 = false;

                if (cA_C.isChecked()) {
                    iscorrect3 = true;
                    atleastOneisChecked = true;

                } else
                    iscorrect3 = false;

                if (cA_D.isChecked()) {
                    iscorrect4 = true;
                    atleastOneisChecked = true;
                } else
                    iscorrect4 = false;

                validation();
                if (!ti_question.getEditText().getText().toString().trim().isEmpty() && !ti_choices1.getEditText().getText().toString().trim().isEmpty() && !ti_choices2.getEditText().getText().toString().trim().isEmpty() && !ti_choices3.getEditText().getText().toString().trim().isEmpty() && !ti_choices4.getEditText().getText().toString().trim().isEmpty() && !ti_timer.getEditText().getText().toString().trim().isEmpty() && atleastOneisChecked == true) {
                    time = Integer.parseInt(ti_timer.getEditText().getText().toString().trim());
                    insertitem(question, choices1, choices2, choices3, choices4, iscorrect1, iscorrect2, iscorrect3, iscorrect4, time);
                    saveData();
                    Intent intent = new Intent(createquestion.this, createquiz.class);
                    createquestion.this.startActivity(intent);
                }


            }
        });
    }

    public void validation() {
        if (ti_question.getEditText().getText().toString().trim().isEmpty())
            ti_question.setError("Cannot be blank");
        if (ti_choices1.getEditText().getText().toString().trim().isEmpty())
            ti_choices1.setError("Cannot be blank");
        if (ti_choices2.getEditText().getText().toString().trim().isEmpty())
            ti_choices2.setError("Cannot be blank");
        if (ti_choices3.getEditText().getText().toString().trim().isEmpty())
            ti_choices3.setError("Cannot be blank");
        if (ti_choices4.getEditText().getText().toString().trim().isEmpty())
            ti_choices4.setError("Cannot be blank");
        if (ti_timer.getEditText().getText().toString().trim().isEmpty())
            ti_timer.setError("Cannot be blank");

            if (atleastOneisChecked == false)
                Toast.makeText(createquestion.this, "At Least One Choices is Selected", Toast.LENGTH_SHORT).show();
        }


    }

