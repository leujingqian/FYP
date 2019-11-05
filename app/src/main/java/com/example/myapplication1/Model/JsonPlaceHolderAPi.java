package com.example.myapplication1.Model;

import com.example.myapplication1.Model.Post;
import com.example.myapplication1.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderAPi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("users/")
    Call<List<User>> getUsers();

    @GET("typicode/demo/comments")
    Call<List<Question>> getQuestion();

    @GET("api/quizzes")
    Call<List<Quizzes>> getQuizzes();
}
