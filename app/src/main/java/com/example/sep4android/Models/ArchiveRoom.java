package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArchiveRoom {


    @SerializedName("id")
    @Expose
    private int roomNumber;
    private Condition co2;
    private Condition humidity;
    private Condition temperature;

    public ArchiveRoom(int roomNumber,Condition co2){
        this.roomNumber=roomNumber;
        this.co2=co2;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Condition getCO2() {
        return co2;
    }

    public Condition getHumidity() {return  humidity;}

    public Condition getTemperature(){return temperature;}
}

