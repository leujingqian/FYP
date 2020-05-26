package com.example.myapplication1.Model;

import java.util.List;

public class Favourite {
    private String quizId;
    private Boolean isFavorited;
    private String _id;
    private Quizzes quiz_id;
    private Boolean isDeleted;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public Favourite(String quizId) {
        this.quizId = quizId;
    }

    public Boolean getFavorited() {
        return isFavorited;
    }

    public void setFavorited(Boolean favorited) {
        isFavorited = favorited;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

   public Quizzes getQuizzes() {
        return quiz_id;
    }

    public void setQuizzes(Quizzes quizzes) {
        this.quiz_id = quizzes;

    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
