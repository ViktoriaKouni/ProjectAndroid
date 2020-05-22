package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArchiveRoom {

    private int roomNumber;
    private String roomName;
    private CO2 co2;
    private Humidity humidity;
    private Temperature temperature;
    private OptimalValues optimalValues;

    public ArchiveRoom(int roomNumber,String roomName,CO2 co2,OptimalValues optimalValues){
        this.roomNumber=roomNumber;
        this.roomName=roomName;
        this.co2=co2;
        this.optimalValues = optimalValues;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public CO2 getCO2() { return co2; }

    public Humidity getHumidity() {return  humidity;}

    public Temperature getTemperature(){return temperature;}

    public OptimalValues getOptimalValues() { return optimalValues; }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof ArchiveRoom))
        {
            return false;
        }
        ArchiveRoom other = (ArchiveRoom) obj;
        return co2.equals(other.getCO2()) && roomNumber == (other.getRoomNumber()) && optimalValues.equals(other.getOptimalValues()) && roomName.equals(other.getRoomName());
    }
}

