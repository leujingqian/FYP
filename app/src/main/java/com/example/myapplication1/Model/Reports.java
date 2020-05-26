package com.example.myapplication1.Model;

import java.util.List;

public class Reports {

    private String game_name;
    private Hoster hoster;
    private String hosted_date;
    private String _id;
    private List player_results;

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public Hoster getHoster() {
        return hoster;
    }

    public void setHoster(Hoster hoster) {
        this.hoster = hoster;
    }

    public String getHosted_date() {
        return hosted_date;
    }

    public void setHosted_date(String hosted_date) {
        this.hosted_date = hosted_date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List getPlayer_results() {
        return player_results;
    }

    public void setPlayer_results(List player_results) {
        this.player_results = player_results;
    }
}

