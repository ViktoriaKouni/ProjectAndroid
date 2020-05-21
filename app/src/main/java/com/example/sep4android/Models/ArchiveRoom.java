package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArchiveRoom {


    @SerializedName("id")
    @Expose
    private int roomNumber;
    @SerializedName("value")
    @Expose
    private double co2;
    private double humidity;
    private double temperature;

    public ArchiveRoom(int roomNumber,int co2){
        this.roomNumber=roomNumber;
        this.co2=co2;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getCO2() {
        return co2;
    }

    public double getHumidity() {return  humidity;}

    public double getTemperature(){return temperature;}
}

