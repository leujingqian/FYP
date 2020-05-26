package com.example.myapplication1.Model;

public class Share {

    private String quizId;
    private String email;
    private boolean isShared;
    private String err;


    public Share(String quizId, String email) {
        this.quizId = quizId;
        this.email = email;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }
}
