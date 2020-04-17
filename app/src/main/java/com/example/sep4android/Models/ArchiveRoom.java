package com.example.sep4android.Models;

public class ArchiveRoom {

    private int roomNumber;
    private CO2 co2;

    public ArchiveRoom(int roomNumber,CO2 co2){
        this.roomNumber=roomNumber;
        this.co2=co2;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public CO2 getCO2() {
        return co2;
    }

    public void setCo2(CO2 co2) {
        this.co2 = co2;
    }
}

