package com.example.myapplication1.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication1.R;

public class CodeFragment extends Fragment {
 private Button confirm;
 public static final String NICKNAME="usernickname";
 public static final String GAMEPIN="gamepin";
 public EditText nickname,gamepin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_code,container,false);
        confirm=(Button)v.findViewById(R.id.button);
        nickname=(EditText)v.findViewById(R.id.nickname);
        gamepin=(EditText)v.findViewById(R.id.gamecode);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nickname.getText().toString().isEmpty() && !gamepin.getText().toString().isEmpty()) {
                    Intent intent = new Intent(getActivity(), LiveGame.class);
                    startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString("nickname",nickname.getText().toString());
                    bundle.putString("gamepin",gamepin.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                else if(nickname.getText().toString().isEmpty()){
                    nickname.setError("Cannot be blank");
                }else if(gamepin.getText().toString().isEmpty()){
                    gamepin.setError("Cannot be blank");
                }
            }
        });



        return v;
    }
}
