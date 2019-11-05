package com.example.myapplication1.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication1.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;

public class HostingGame extends AppCompatActivity {
    private Socket socket;
    public String quizid, gametype;
    public View v;
    public TextView gamepin,showtimer,player;
    public String btnstate="true";
    public Boolean nextstate = true;
    final JSONObject questiondetail = new JSONObject();
    final JSONObject questiondetail2 = new JSONObject();
    public Button start,A,B,C,D,timerbutton,finish;
    public int timer1;
    public CountDownTimer yourCountDownTimer;
    public int ans1,ans2,ans3,ans4;
    public long second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingpage);
        A=findViewById(R.id.button2);
        B=findViewById(R.id.button3);
        C=findViewById(R.id.button4);
        D=findViewById(R.id.button5);

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
        if (getIntent().getStringExtra("quizid") != null && (getIntent().getStringExtra("quiztype") != null)) {
            quizid = b.getString("quizid");
            gametype = b.getString("quiztype");
        }
        JSONObject hostdetail = new JSONObject();
        try {
            hostdetail.put("quizId", quizid);
            hostdetail.put("gameType", "mcq");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            socket=IO.socket("http://192.168.0.173:3000/");//192.168.43.18
            socket.connect();
            socket.emit("host-game", hostdetail, new Ack() {
                @Override
                public void call(final Object... args) {
                    final Boolean response1=(Boolean) args[0];
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {

                                           if(response1== true) {
                                               Toast.makeText(HostingGame.this, "Successfully Create", Toast.LENGTH_SHORT).show();
                                               JSONObject object = (JSONObject) args[1];

                                               try {
                                                   gamepin.setText("Game Pin:" + object.getString("pin"));
                                               } catch (Exception e) {
                                                   e.printStackTrace();
                                               }
                                               socket.on("display-name", new Emitter.Listener() {
                                                   @Override
                                                   public void call(final Object... args) {
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               player.setText("");

                                                               try {
                                                                   JSONArray jsonarray1 = (JSONArray) args[0];
                                                                   String name = "";
                                                                   for (int i = 0; i <= jsonarray1.length(); i++) {
                                                                       name += jsonarray1.getString(i) + "\n";
                                                                       player.setText(name);
                                                                       Toast.makeText(HostingGame.this, socket.id(), Toast.LENGTH_SHORT).show();
                                                                   }

                                                               } catch (Exception e) {
                                                                   e.printStackTrace();
                                                               }

                                                           }
                                                       });
                                                   }

                                               });
                                           }else{
                                               finish();
                                           }


                        }
                    });
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                player.setVisibility(v.INVISIBLE);
                start.setText("Next");
                A.setVisibility(v.VISIBLE);
                B.setVisibility(v.VISIBLE);
                C.setVisibility(v.VISIBLE);
                D.setVisibility(v.VISIBLE);
                showtimer.setVisibility(v.INVISIBLE);
                player.setVisibility(v.INVISIBLE);
                /*start.setText("Next");*/
                JSONObject questiondetail=new JSONObject();
                try{
                    questiondetail.put("btnState",nextstate);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                Toast.makeText(HostingGame.this, "fuck", Toast.LENGTH_SHORT).show();
                socket.emit("next-question", questiondetail, new Ack() {
                    @Override
                    public void call(final Object... args) {

                        runOnUiThread(new Runnable() {
                            String response=(String) args[0];
                            @Override
                            public void run() {
                                timerbutton.setVisibility(v.VISIBLE);
                                JSONObject nextquestion=(JSONObject)args[1];
                                if(response.equals("true")){
                                    try{
                                        Toast.makeText(HostingGame.this, ""+response, Toast.LENGTH_SHORT).show();
                                                       gamepin.setText(nextquestion.getJSONObject("question").getString("question"));
                                                       String syncresponse=nextquestion.getString("question");
                                                       JSONObject object2 = new JSONObject(syncresponse);
                                                       JSONArray choices = object2.getJSONArray("choices");
                                                       timer1 = nextquestion.getJSONObject("question").getInt("timer");
                                                       player.setText("");
                                                      /* for(int i=0;i<=choices.length();i++)
                                                       {*/
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
                                                           nextstate=false;
//                                                       }


                                         }catch (Exception e)
                                        {
                                        e.printStackTrace();
                                         }
                                         yourCountDownTimer=new  CountDownTimer(timer1*1000,1000){
                                        @Override
                                        public void onTick(final long millisUntilFinished) {
                                            second=millisUntilFinished/1000;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    showtimer.setText("seconds remaining: " + second);
                                                    timerbutton.setText(""+second);
                                                }
                                            });


                                            socket.emit("time-left",millisUntilFinished/1000);
                                        }

                                        @Override
                                        public void onFinish() {
                                            nextstate=false;
                                            start.performClick();
                                            yourCountDownTimer.cancel();
                                            showtimer.setText("");

                                        }
                                         }.start();


                                }else if(response.equals("false")){
                                    timerbutton.setVisibility(v.INVISIBLE);
                                    yourCountDownTimer.cancel();
                                    showtimer.setText("");
                                     JSONObject summary=(JSONObject) args[1];
                                    player.setVisibility(v.VISIBLE);
                                    try{
                                        ans1=summary.getInt("ans1");
                                         ans2=summary.getInt("ans2");
                                         ans3=summary.getInt("ans3");
                                         ans4=summary.getInt("ans4");


                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showtimer.setVisibility(View.VISIBLE);
                                            showtimer.setText("Ans1 "+ans1+" Ans2 "+ans2+" Ans3 "+ans3+" Ans4 "+ans4);
                                            Toast.makeText(HostingGame.this, ""+ans1, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    nextstate=true;
                                }
                                else if(response.equals("over")){
                                    gamepin.setText("ScoreBoard");
                                    A.setVisibility(v.INVISIBLE);
                                    B.setVisibility(v.INVISIBLE);
                                    C.setVisibility(v.INVISIBLE);
                                    D.setVisibility(v.INVISIBLE);
                                    timerbutton.setVisibility(v.INVISIBLE);
                                    showtimer.setVisibility(v.VISIBLE);
                                    JSONObject conclude=(JSONObject)args[1];
                                    try {
                                        JSONArray scoreboard=conclude.getJSONArray("scoreBoard");
                                        SpannableString content=new SpannableString("\tName          \tScore\n");
                                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                        showtimer.setText(content);
                                        for(int i=0;i<scoreboard.length();i++){
                                            JSONObject scoreboard1=scoreboard.getJSONObject(i);
                                            String scoreName=i+1+" \t"+scoreboard1.getString("name")+"\t          "+scoreboard1.getInt("score")+"\n";

                                            showtimer.append(scoreName);
                                        }
                                        start.setVisibility(v.INVISIBLE);
                                        finish.setVisibility(v.VISIBLE);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

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
        finish();
        socket.disconnect();
    }



}
