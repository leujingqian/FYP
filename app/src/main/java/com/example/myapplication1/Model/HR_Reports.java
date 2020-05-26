package com.example.myapplication1.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HR_Reports {

    @SerializedName("hosterReport")
    @Expose
    private HR_HosterReport hosterReport;
    @SerializedName("playerReport")
    @Expose
    private List<Object> playerReport = null;

    public HR_HosterReport getHosterReport() {
        return hosterReport;
    }

    public void setHosterReport(HR_HosterReport hosterReport) {
        this.hosterReport = hosterReport;
    }

    public List<Object> getPlayerReport() {
        return playerReport;
    }

    public void setPlayerReport(List<Object> playerReport) {
        this.playerReport = playerReport;
    }


}