package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayedReport {
    @SerializedName("played_date")
    @Expose
    private String playedDate;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("correct")
    @Expose
    private Integer correct;
    @SerializedName("incorrect")
    @Expose
    private Integer incorrect;
    @SerializedName("unattempted")
    @Expose
    private Integer unattempted;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("socket_id")
    @Expose
    private String socketId;
    @SerializedName("game_id")
    @Expose
    private String gameId;
    @SerializedName("player")
    @Expose
    private Player player;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("hoster_name")
    @Expose
    private String hosterName;
    @SerializedName("hoster_report_id")
    @Expose
    private String hosterReportId;
    @SerializedName("questions")
    @Expose
    private List<Played_QUestion> questions = null;
    @SerializedName("scoreboard")
    @Expose
    private List<Scoreboard> scoreboard = null;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getPlayedDate() {
        return playedDate;
    }

    public void setPlayedDate(String playedDate) {
        this.playedDate = playedDate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(Integer incorrect) {
        this.incorrect = incorrect;
    }

    public Integer getUnattempted() {
        return unattempted;
    }

    public void setUnattempted(Integer unattempted) {
        this.unattempted = unattempted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getHosterName() {
        return hosterName;
    }

    public void setHosterName(String hosterName) {
        this.hosterName = hosterName;
    }

    public String getHosterReportId() {
        return hosterReportId;
    }

    public void setHosterReportId(String hosterReportId) {
        this.hosterReportId = hosterReportId;
    }

    public List<Played_QUestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Played_QUestion> questions) {
        this.questions = questions;
    }

    public List<Scoreboard> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(List<Scoreboard> scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
