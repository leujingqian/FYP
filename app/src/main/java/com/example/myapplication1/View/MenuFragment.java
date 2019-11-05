package com.example.myapplication1.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication1.Model.JsonPlaceHolderAPi;
import com.example.myapplication1.Model.Post;
import com.example.myapplication1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {
  private TextView viewmenu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu,container,false);
         viewmenu=(TextView) view.findViewById(R.id.view_menu);

            Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.co")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderAPi jsonPlaceHolderAPi=retrofit.create(JsonPlaceHolderAPi.class);
        Call<List<Post>> call=jsonPlaceHolderAPi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    viewmenu.setText("Code"+response.code());
                    return;
                }
                List<Post> posts=response.body();
                for(Post post:posts){
                    String content="";
                    content+="userid："+post.getUserId()+"\n";
                    content+="id："+post.getId()+"\n";
                    content+="title："+post.getTitle()+"\n";
                    content+="body："+post.getText()+"\n\n";

                    viewmenu.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                viewmenu.setText(t.getMessage());

            }
        });

return view;
    }


}
