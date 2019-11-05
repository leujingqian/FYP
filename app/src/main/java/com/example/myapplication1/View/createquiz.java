package com.example.myapplication1.View;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication1.Adapter.QuestionAdapter;
import com.example.myapplication1.Model.Question;
import com.example.myapplication1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class createquiz extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private QuestionAdapter madapter;
    private ArrayList<Question> question_list = new ArrayList<>();
    private Button createquestion,upload;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquiz);
        loadData();
        buildRecyclerView();
        createquestion = findViewById(R.id.bt_addquestion);
        createquestion.setOnClickListener(new View.OnClickListener() {
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
                sharedPreferences.edit().clear().apply();
                finish();
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
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Question list", null);
        Type type = new TypeToken<ArrayList<Question>>() {}.getType();
        question_list = gson.fromJson(json, type);

        if (question_list == null) {
            question_list = new ArrayList<>();
        }
    }






}
