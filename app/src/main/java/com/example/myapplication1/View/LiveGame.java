package com.example.myapplication1.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Adapter.notificationadapter;
import com.example.myapplication1.Adapter.scoreboard_adapter;
import com.example.myapplication1.Model.Scoreboard;
import com.example.myapplication1.Model.notification_model;
import com.example.myapplication1.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class LiveGame extends AppCompatActivity {
    public Socket socket;
    public String Nickname, GamePin;
    public Boolean result=false;
    public int clickedtime;
    public int questionreceived;
    public View v;
    public TextView questionnum,studentsummary,result1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_game);
        Bundle b = getIntent().getExtras();
        if (getIntent().getStringExtra("nickname") != null && (getIntent().getStringExtra("gamepin") != null)) {
            Nickname = b.getString("nickname");
            GamePin = b.getString("gamepin");
        }
        JSONObject userdetail = new JSONObject();
        try {
            userdetail.put("name",  Nickname);
            userdetail.put("gameId", GamePin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferencetoken =getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String token = sharedPreferencetoken.getString("Token", null);
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.query = "auth_token=" + token;
//             Socket socket = IO.socket("https://collaborative-learning-system.herokuapp.com/", opts);
            socket=IO.socket("https://collaborative-learning-system.herokuapp.com/",opts);
            socket.connect();
            socket.emit("join-game", userdetail, new Ack() {
                @Override
                public void call(final Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           JSONObject object=(JSONObject) args[0];
                           try {
                               if(object.getBoolean("isJoined")==false){
                                   Toast.makeText(LiveGame.this,object.getString("error"),Toast.LENGTH_LONG).show();
                                   finish();
                                   socket.disconnect();
                               }
                               else{
                                   Toast.makeText(LiveGame.this,"Correct",Toast.LENGTH_LONG).show();
                                   setContentView(R.layout.waitingpage);
                                   TextView gamepin = (TextView) findViewById(R.id.textView);
                                   TextView nickaname = (TextView) findViewById(R.id.textView3);
                                   gamepin.setText("  PIN: "+GamePin);
                                   nickaname.setText("  "+Nickname);
                               }

                           }catch (Exception e){
                               e.printStackTrace();
                           }


                        }
                    });
                }
            });
            socket.on("player-next-question", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    socket.emit("receive-question");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            questionreceived=1;
                            JSONObject choicesdetail = (JSONObject) args[0];
                            setContentView(R.layout.chooseanswer);
                            studentsummary=findViewById(R.id.studentsummary);
                            studentsummary.setVisibility(v.INVISIBLE);
                            result1=findViewById(R.id.result);
                            result1.setVisibility(v.INVISIBLE);
                            try {
                               questionnum =findViewById(R.id.questionnumber);
                                int questionindex=choicesdetail.getInt("questionIndex");
                                int questionlength=choicesdetail.getInt("questionLength");
                                questionnum.setText("" + questionindex+"\\"+questionlength );
                                JSONArray jsonArray=choicesdetail.getJSONArray("choiceIds");
                                final String choicesA=jsonArray.getString(0);
                                final String choicesB=jsonArray.getString(1);
                                final String choicesC=jsonArray.getString(2);
                                final String choicesD=jsonArray.getString(3);
                                /*tv1.setText(jsonArray.getString(0));
                                tv2.setText(jsonArray.getString(1));
                                tv3.setText(jsonArray.getString(2));
                                tv4.setText(jsonArray.getString(3));*/
                                final Button A=findViewById(R.id.A);
                                final Button B=findViewById(R.id.B);
                                final Button C=findViewById(R.id.C);
                                final Button D=findViewById(R.id.D);
                                A.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        result1.setVisibility(v.VISIBLE);
                                        result1.setText("Waiting for other ppl");
                                        A.setVisibility(v.INVISIBLE);
                                        B.setVisibility(v.INVISIBLE);
                                        C.setVisibility(v.INVISIBLE);
                                        D.setVisibility(v.INVISIBLE);
                                        socket.emit("player-answer",choicesA, new Ack() {
                                            @Override
                                            public void call(Object... args) {
                                                Boolean response=(Boolean)args[0];
                                                result=response;
                                                clickedtime=1;

                                            }
                                        });
                                    }
                                });
                                B.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        result1.setVisibility(v.VISIBLE);
                                        result1.setText("Waiting for other ppl");
                                        A.setVisibility(v.INVISIBLE);
                                        B.setVisibility(v.INVISIBLE);
                                        C.setVisibility(v.INVISIBLE);
                                        D.setVisibility(v.INVISIBLE);
                                        socket.emit("player-answer",choicesB, new Ack() {
                                            @Override
                                            public void call(Object... args) {
                                                Boolean response=(Boolean)args[0];
                                                result=response;
                                                clickedtime=1;
                                                /*runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        setContentView(R.layout.afterchoosingquestion);
                                                        waiting.setVisibility(v.VISIBLE);
                                                        result1.setVisibility(v.INVISIBLE);
                                                    }
                                                });*/
                                            }
                                        });
                                    }
                                });
                                C.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        result1.setVisibility(v.VISIBLE);
                                        result1.setText("Waiting for other ppl");
                                        A.setVisibility(v.INVISIBLE);
                                        B.setVisibility(v.INVISIBLE);
                                        C.setVisibility(v.INVISIBLE);
                                        D.setVisibility(v.INVISIBLE);
                                        socket.emit("player-answer",choicesC, new Ack() {
                                            @Override
                                            public void call(Object... args) {
                                                Boolean response=(Boolean)args[0];
                                                result=response;
                                                clickedtime=1;
                                               /* runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        setContentView(R.layout.afterchoosingquestion);
                                                        waiting.setVisibility(v.VISIBLE);
                                                        result1.setVisibility(v.INVISIBLE);
                                                    }
                                                });*/
                                            }
                                        });
                                    }
                                });
                                D.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        result1.setVisibility(v.VISIBLE);
                                        result1.setText("Waiting for other ppl");
                                        A.setVisibility(v.INVISIBLE);
                                        B.setVisibility(v.INVISIBLE);
                                        C.setVisibility(v.INVISIBLE);
                                        D.setVisibility(v.INVISIBLE);
                                        socket.emit("player-answer",choicesD, new Ack() {
                                            @Override
                                            public void call(Object... args) {
                                                Boolean response=(Boolean)args[0];
                                                result=response;
                                                clickedtime=1;
                                               /* runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        result1.setText("Waiting for other ppl");
                                                        A.setVisibility(v.INVISIBLE);
                                                        B.setVisibility(v.INVISIBLE);
                                                        C.setVisibility(v.INVISIBLE);
                                                        D.setVisibility(v.INVISIBLE);
                                                    }
                                                });*/
                                            }
                                        });
                                    }
                                });
                                /*if(clickedtime==1) {
                                    result1.setText("Waiting for other ppl");
                                    A.setVisibility(v.INVISIBLE);
                                    B.setVisibility(v.INVISIBLE);
                                    C.setVisibility(v.INVISIBLE);
                                    D.setVisibility(v.INVISIBLE);
                                }*/
                                /*if(clickedtime==1) {
                                    setContentView(R.layout.afterchoosingquestion);
                                    waiting.setVisibility(v.VISIBLE);
                                    result1.setVisibility(v.INVISIBLE);
                                }*/
                                socket.on("get-question-results", new Emitter.Listener() {
                                    @Override
                                    public void call(Object... args) {
                                        socket.emit("question-results", new Ack() {
                                            @Override
                                            public void call(Object... args) {
                                                final JSONObject questionresuls=(JSONObject) args[0] ;
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            if(questionresuls.getBoolean("answerResult")==true){
                                                                result1.setBackgroundColor(Color.parseColor("#00FF00"));
                                                                result1.setText("Correct\n Congratulation ！\n Rank: "+questionresuls.getInt("rank")+"\n Points:"+questionresuls.getInt("points"));

                                                            }else {
                                                                result1.setBackgroundColor(Color.parseColor("#FF0000"));
                                                                result1.setText("Incorrect\n Nobody's perfect\n Rank: "+questionresuls.getInt("rank")+"\n Points:"+questionresuls.getInt("points"));
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        result1.setVisibility(v.VISIBLE);
                                                        A.setVisibility(v.INVISIBLE);
                                                        B.setVisibility(v.INVISIBLE);
                                                        C.setVisibility(v.INVISIBLE);
                                                        D.setVisibility(v.INVISIBLE);

                                                    }
                                                });
                                            }
                                        });

                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on("player-game-over", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("get-overall-results", new Ack() {
                    @Override
                    public void call(Object... args) {
                        final JSONObject totalresult= (JSONObject) args[0];

                                               /* setContentView(R.layout.studentsummary);
                                                TextView correct=findViewById(R.id.correct);
                                                TextView incorrect=findViewById(R.id.incorrect);
                                                TextView unattempt=findViewById(R.id.unattempt);*/
                        try{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    studentsummary.setVisibility(v.VISIBLE);
                                    result1.setVisibility(v.INVISIBLE);
                                    try {

                                        questionnum.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
                                        questionnum.setTextSize(TypedValue.COMPLEX_UNIT_IN,0.35f);
                                        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                        questionnum.setTypeface(boldTypeface);
                                        questionnum.setText("Game Over");
                                        studentsummary.setTextSize(TypedValue.COMPLEX_UNIT_IN,0.15f);
                                        studentsummary.setGravity(Gravity.CENTER_HORIZONTAL);
                                        studentsummary.setTypeface(boldTypeface);
                                        studentsummary.setText("\n\nScore :" + totalresult.getInt("points") + "\n" + "Correct :" + totalresult.getInt("correct") + "\n" + "Incorrect: " + totalresult.getInt("incorrect")+"\n" + "Unattempted: " + totalresult.getInt("unattempted"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        socket.emit("disconnect");
                    }
                });
            }
        });
        socket.on("hoster-disconnect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("disconnect");
              socket.disconnect();
              finish();
            }
        });


    }
    public void onBackPressed() {
        socket.disconnect();
        socket.disconnect();
        finish();
    }
}
