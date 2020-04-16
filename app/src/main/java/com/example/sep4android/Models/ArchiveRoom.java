package com.example.sep4android.Models;

public class ArchiveRoom {

    private int roomNumber;
    private CO2 co2Level;

    public ArchiveRoom(int roomNumber,CO2 co2Level){
        this.roomNumber=roomNumber;
        this.co2Level=co2Level;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public CO2 getCO2Level() {
        return co2Level;
    }

    public void setCo2Level(CO2 co2Level) {
        this.co2Level = co2Level;
    }
}

