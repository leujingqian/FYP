package com.example.myapplication1.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.Adapter.notificationadapter;
import com.example.myapplication1.Adapter.scoreboard_adapter;
import com.example.myapplication1.Model.Classes;
import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.QuestionList;
import com.example.myapplication1.Model.Scoreboard;
import com.example.myapplication1.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HostingGame extends AppCompatActivity {
    private Socket socket;
    public String quizid, gametype,assign;
    public View v;
    public TextView gamepin,showtimer,player,choice1,choice2,choice3,choice4;
    public String btnstate="true";
    public Boolean nextstate = true;
    final JSONObject questiondetail = new JSONObject();
    final JSONObject questiondetail2 = new JSONObject();
    ArrayList<Classes> assignclasslist = new ArrayList<>();
    private RecyclerView mrecyclerView;
    private ArrayList<Scoreboard> scoreboardslist=new ArrayList<>();
    private scoreboard_adapter adapter;

    public Button start,A,B,C,D,timerbutton,finish;
    public int timer1;
    public CountDownTimer yourCountDownTimer,movethrough;
    public int ans1,ans2,ans3,ans4;
    public long second;
    public boolean shufflequestion,shufflechoices,questiontimer,automove,isCorrect1,isCorrect2,isCorrect3,isCorrect4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingpage);
        A=findViewById(R.id.button2);
        B=findViewById(R.id.button3);
        C=findViewById(R.id.button4);
        D=findViewById(R.id.button5);
        choice1=findViewById(R.id.choices1);
        choice2=findViewById(R.id.choices2);
        choice3=findViewById(R.id.choices3);
        choice4=findViewById(R.id.choices4);

        choice1.setVisibility(View.INVISIBLE);
        choice2.setVisibility(View.INVISIBLE);
        choice3.setVisibility(View.INVISIBLE);
        choice4.setVisibility(View.INVISIBLE);
        start=findViewById(R.id.start);
        finish=findViewById(R.id.button7);
        finish.setVisibility(v.INVISIBLE);
        timerbutton=findViewById(R.id.button6);
        timerbutton.setVisibility(v.INVISIBLE);
        gamepin=findViewById(R.id.timer);
        player=findViewById(R.id.host_playersname);
        showtimer=findViewById(R.id.host_gamepin);
        A.setVisibility(v.INVISIBLE);
        B.setVisibility(v.INVISIBLE);
        C.setVisibility(v.INVISIBLE);
        D.setVisibility(v.INVISIBLE);
        showtimer.setVisibility(v.INVISIBLE);

        Bundle b = getIntent().getExtras();
        if (getIntent().getStringExtra("quizid") != null ) {
            quizid = b.getString("quizid");
            shufflequestion=b.getBoolean("shufflequestion",false);
            shufflechoices=b.getBoolean("shufflechoices",false);
            questiontimer=b.getBoolean("questiontimer",false);
            automove=b.getBoolean("automove",false);
            assign=b.getString("assignclass");

        }
        else
            Toast.makeText(HostingGame.this,"No id", Toast.LENGTH_SHORT).show();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Classes>>() {
        }.getType();
        assignclasslist = gson.fromJson(assign, type);

        JSONArray arr=new JSONArray();
        for(int i=0;i<assignclasslist.size();i++){
            arr.put(assignclasslist.get(i).getClassId());
        }
        JSONObject hostdetail = new JSONObject();
        try {
            hostdetail.put("quizId", quizid);
            hostdetail.put("suffleQuestions",shufflequestion);
            hostdetail.put("suffleAnswerOptions",shufflechoices);
            hostdetail.put("assignClassIds",arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mrecyclerView = (RecyclerView) findViewById(R.id.live_scoreboard);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setHasFixedSize(true);
        SharedPreferences sharedPreferencetoken =getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String token = sharedPreferencetoken.getString("Token", null);
        try{
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.query = "auth_token=" + token;
//             Socket socket = IO.socket("https://collaborative-learning-system.herokuapp.com/", opts);
            socket=IO.socket("https://collaborative-learning-system.herokuapp.com/",opts);//192.168.43.18
            socket.connect();
        }catch(Exception e){
            e.printStackTrace();
        }
            socket.emit("host-game", hostdetail, new Ack() {
                @Override
                public void call(final Object... args) {
                    final JSONObject object=(JSONObject)args[0];
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          try {
                                              if(object.getBoolean("isHosted")==true){

                                                  gamepin.setText("Game Pin: "+object.getString("gameId"));
                                                  Toast.makeText(HostingGame.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                                 player.setBackgroundColor(Color.parseColor("#6769f0"));
                                                 player.setTextColor(Color.parseColor("#ffffff"));
                                                  socket.on("display-name", new Emitter.Listener() {
                                                      @Override
                                                      public void call(final Object... args) {
                                                          runOnUiThread(new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  player.setText("");

                                                                  try{
                                                                      JSONArray jsonarray1 = (JSONArray) args[0];
                                                                      String name = "";
                                                                      for (int i = 0; i <= jsonarray1.length(); i++) {
                                                                          name += jsonarray1.getString(i) + "\n";
                                                                          player.setText(name);
                                                                      }
                                                                  }catch (Exception e){
                                                                      e.printStackTrace();
                                                                  }
                                                              }
                                                          });
                                                      }
                                                  });
                                              }
                                              else{
                                                  Toast.makeText(HostingGame.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                                  finish();
                                                  socket.disconnect();
                                              }
                                          }catch (Exception e){
                                              e.printStackTrace();
                                          }




                        }
                    });
                }
            });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                player.setVisibility(v.INVISIBLE);
                start.setText("Next");
                choice1.setVisibility(View.INVISIBLE);
                choice2.setVisibility(View.INVISIBLE);
                choice3.setVisibility(View.INVISIBLE);
                choice4.setVisibility(View.INVISIBLE);
                A.setVisibility(v.VISIBLE);
                B.setVisibility(v.VISIBLE);
                C.setVisibility(v.VISIBLE);
                D.setVisibility(v.VISIBLE);
                showtimer.setVisibility(v.INVISIBLE);
                player.setVisibility(v.INVISIBLE);
                /*start.setText("Next");*/
                /*final JSONObject questiondetail=new JSONObject();
                try{
                    questiondetail.put("btnState",true);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
//                Toast.makeText(HostingGame.this, "fuck", Toast.LENGTH_SHORT).show();
                socket.emit("next-question", nextstate, new Ack() {
                    @Override
                    public void call(final Object... args) {

                        runOnUiThread(new Runnable() {
//                            String response=(String) args[0];
                            JSONObject question=(JSONObject)args[0];
                            @Override
                            public void run() {
                                timerbutton.setVisibility(v.VISIBLE);
                                try{
                                    if(question.getBoolean("nextQuestion")==true&& question.getBoolean("isGameOver")==false){
                                        gamepin.setText(question.getJSONObject("nextQuestionData").getJSONObject("question").getString("question"));
                                        String syncresponse=question.getJSONObject("nextQuestionData").getString("question");
                                        JSONObject object2 = new JSONObject(syncresponse);
                                        JSONArray choices = object2.getJSONArray("choices");
                                        timer1 = question.getJSONObject("nextQuestionData").getJSONObject("question").getInt("timer");
                                        player.setText("");
//                                        for(int i=0;i<=choices.length();i++){
                                            String choice = "";
                                            JSONObject object3 = choices.getJSONObject(0);
                                            JSONObject object4=choices.getJSONObject(1);
                                            JSONObject object5 = choices.getJSONObject(2);
                                            JSONObject object6=choices.getJSONObject(3);
                                            String choiceA = object3.getString("choice");
                                            String choiceB = object4.getString("choice");
                                            String choiceC = object5.getString("choice");
                                            String choiceD = object6.getString("choice");
                                            A.setText(choiceA);
                                            B.setText(choiceB);
                                            C.setText(choiceC);
                                            D.setText(choiceD);
                                        A.setBackgroundColor(Color.parseColor("#e21a3b"));
                                        B.setBackgroundColor(Color.parseColor("#6769f0"));
                                        C.setBackgroundColor(Color.parseColor("#16d46c"));
                                        D.setBackgroundColor(Color.parseColor("#f9b516"));
                                            isCorrect1=object3.getBoolean("is_correct");
                                            isCorrect2=object4.getBoolean("is_correct");
                                             isCorrect3=object5.getBoolean("is_correct");
                                          isCorrect4=object6.getBoolean("is_correct");
                                            nextstate=false;

//                                        }
                                        if(questiontimer==true) {
                                            yourCountDownTimer = new CountDownTimer(timer1 * 1000, 1000) {
                                                @Override
                                                public void onTick(final long millisUntilFinished) {
                                                    second = millisUntilFinished / 1000;
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
//                                                    showtimer.setText("seconds remaining: " + second);
                                                            timerbutton.setText("" + second);
                                                        }
                                                    });


                                                    socket.emit("time-left", millisUntilFinished / 1000);
                                                }

                                                @Override
                                                public void onFinish() {
                                                    nextstate = false;
                                                    start.performClick();
                                                    yourCountDownTimer.cancel();
                                                    showtimer.setText("");

                                                }
                                            }.start();
                                        }else{
                                            timerbutton.setVisibility(v.INVISIBLE);
                                            nextstate=false;
                                        }

                                    }else if(question.getBoolean("nextQuestion")==false&&question.getBoolean("isGameOver")==false) {
                                        if(questiontimer==true) {
                                            timerbutton.setVisibility(v.INVISIBLE);
                                            yourCountDownTimer.cancel();
                                            showtimer.setText("");
                                        }
                                        /*player.setVisibility(v.VISIBLE);*/
                                        try {
                                            ans1 = question.getJSONObject("nextQuestionData").getJSONObject("questionResults").getInt("choice1");
                                            ans2 = question.getJSONObject("nextQuestionData").getJSONObject("questionResults").getInt("choice2");
                                            ans3 = question.getJSONObject("nextQuestionData").getJSONObject("questionResults").getInt("choice3");
                                            ans4 = question.getJSONObject("nextQuestionData").getJSONObject("questionResults").getInt("choice4");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                timerbutton.setVisibility(v.INVISIBLE);
                                                showtimer.setVisibility(View.INVISIBLE);
                                                choice1.setVisibility(View.VISIBLE);
                                                choice2.setVisibility(View.VISIBLE);
                                                choice3.setVisibility(View.VISIBLE);
                                                choice4.setVisibility(View.VISIBLE);
                                                choice1.setText(ans1+"");
                                                choice2.setText(ans2+"");
                                                choice3.setText(ans3+"");
                                                choice4.setText(ans4+"");
                                                showtimer.setText("Ans1 "+ans1+" Ans2 "+ans2+" Ans3 "+ans3+" Ans4 "+ans4);
                                                Toast.makeText(HostingGame.this, ""+ans1, Toast.LENGTH_SHORT).show();
                                                if(isCorrect1==false){
                                                A.setBackgroundColor(Color.parseColor("#6e2130"));
                                                }
                                                if(isCorrect2==false){
                                                    B.setBackgroundColor(Color.parseColor("#3d4079"));
                                                }
                                                if(isCorrect3==false){
                                                    C.setBackgroundColor(Color.parseColor("#1d6b43"));
                                                }
                                                if(isCorrect4==false){
                                                    D.setBackgroundColor(Color.parseColor("#775f21"));
                                                }
                                            }
                                        });
                                        nextstate=true;
                                        if(automove==true){
                                            movethrough=new CountDownTimer(5*1000,1000){
                                                @Override
                                                public void onTick(long millisUntilFinished) {

                                                }

                                                @Override
                                                public void onFinish() {
                                                    start.performClick();
                                                }
                                            }.start();

                                        }
                                    }

                                    else if(question.getBoolean("nextQuestion")==false&&question.getBoolean("isGameOver")==true){
                                        gamepin.setText("ScoreBoard");
                                        A.setVisibility(v.INVISIBLE);
                                        B.setVisibility(v.INVISIBLE);
                                        C.setVisibility(v.INVISIBLE);
                                        D.setVisibility(v.INVISIBLE);
                                        timerbutton.setVisibility(v.INVISIBLE);
                                        showtimer.setVisibility(v.INVISIBLE);
                                        try {
                                            JSONArray scoreboard=question.getJSONObject("nextQuestionData").getJSONArray("scoreBoard");
                                            SpannableString content=new SpannableString("\tName          \tScore\n");
                                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                            showtimer.setText(content);
                                            for(int i=0;i<scoreboard.length();i++){
                                                JSONObject scoreboard1=scoreboard.getJSONObject(i);
                                                String scoreName=i+1+" \t"+scoreboard1.getString("name")+"\t          "+scoreboard1.getInt("points")+"\n";
                                                scoreboardslist.add(new Scoreboard(scoreboard1.getInt("points"),scoreboard1.getString("name")));
                                                showtimer.append(scoreName);
                                            }
                                            adapter=new scoreboard_adapter(scoreboardslist,getApplicationContext());
                                            mrecyclerView.setAdapter(adapter);
                                            start.setVisibility(v.INVISIBLE);
                                            finish.setVisibility(v.VISIBLE);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }



                            }
                        });
                    }
                });
            }
        });
        socket.on("display-summary", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nextstate=false;
                        start.performClick();
                        yourCountDownTimer.cancel();

                    }
                });
            }

        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                socket.disconnect();
            }
        });


    }
    public void onBackPressed() {
        socket.disconnect();
        socket.emit("disconnect");
    finish();

    }



}
