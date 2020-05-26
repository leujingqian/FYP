package com.example.myapplication1.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("typicode/demo/comments")
    Call<List<Question>> getQuestion();

    @GET("api/quizzes")
    Call<List<Quizzes>> getQuizzes();

    /*@FormUrlEncoded
    @POST("/api/users/login")
    Call<Register> callRegister(
                @Field("name") String name,
                @Field("email") String email,
                @Field("password")String password,
                @Field("password2") String password2
    );*/

    @POST("/api/users/register")
    Call<Register> callRegister(@Body Register register);

    @POST("/api/users/login")
    Call<Login> callLogin(@Body Login loginuser);

    @POST("api/quizzes")
    Call<Quizzes> callQuizzes(@Header("Authorization") String token ,@Body Quizzes quizzes);
//    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJfaWQiOiI1ZGNhMmRlZDEyMDhjZjM0NmMxOTQ5MjYiLCJuYW1lIjoibXluYW1lIiwiZW1haWwiOiJteWVtYWlsQGdhbWlsLmNvbSJ9LCJpYXQiOjE1NzQwOTgxNzV9.lY_AWMYRnU8tbde9YEBdF7w0p7DptKZlrW66Kd0tUGM'

    @GET ("/api/reports/hoster/")
    Call<List<Reports>> getReports(@Header("Authorization") String token);

    @GET("/api/quizzes/{quizid}")
    Call<Quizid> getQUizzesid(@Header("Authorization") String token,@Path("quizid") String quizid);
    @GET ("/api/reports/player/")
    Call<List<PlayedReport>> getPersonalReports(@Header("Authorization") String token);

    @GET ("/api/reports/hoster/{reportId}")
    Call <HR_Reports>getReportsDetail(@Header("Authorization") String token, @Path("reportId") String reportid);

    @GET ("/api/library/favorites/")
    Call <List<Favourite>> getFavourites( @Header("Authorization") String token);

    @POST ("/api/library/favorites/")
    Call <Favourite> postfavourite(@Header("Authorization") String token, @Body Favourite favourite);

    @POST ("/api/library/shared/")
    Call<Share> postshared(@Header("Authorization") String token, @Body Share share);

    @POST("/api/classes/")
    Call<Createclass> postclass(@Header("Authorization")String token,@Body Createclass createclass);

    @POST("api/members")
    Call<Joinclass> joinclass(@Header("Authorization")String token,@Body Joinclass joinclass);
    @GET("/api/classes/")
    Call<List<Classes>> getclasses(@Header("Authorization")String token);

    @DELETE ("/api/classes/{classesid}")
    Call<Classes> deleteclasses(@Header("Authorization")String token,@Path("classesid") String classid);

    @PATCH("/api/classes/{classesid}")
    Call<Createclass> editclasses(@Header("Authorization")String token,@Path("classesid") String classid,@Body Createclass createclass);

    @GET("/api/classes/{classesid}")
    Call<createclasses> getmember(@Header("Authorization")String token,@Path("classesid") String classid);

    @HTTP(method = "DELETE",path="/api/members/remove/{memberid}",hasBody = true)
    Call<Classes> deletemember(@Header("Authorization") String token,@Path("memberid") String memberid,@Body Classes classes);

    @HTTP(method = "DELETE",path="/api/members/",hasBody = true)
    Call<Classes> unerolled(@Header("Authorization") String token,@Body Classes classes);

    @GET ("/api/library/shared/")
    Call <List<Favourite>> getShared( @Header("Authorization") String token);

    @GET ("/api/reports/hoster/{reportId}")
    Call <List<HR_Question>>getReportsAccuracyDetail(@Header("Authorization") String token, @Path("reportId") String reportid);

    @DELETE ("/api/library/favorites/{quizid}")
    Call<Favourite> unfavourite(@Header("Authorization")String token,@Path("quizid") String quizid);

    @GET ("/api/reports/player/{reportId}")
    Call <PlayedReport>getPlayedReportDetail(@Header("Authorization") String token, @Path("reportId") String reportid);


}